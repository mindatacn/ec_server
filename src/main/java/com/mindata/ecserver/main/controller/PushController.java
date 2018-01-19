package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultCode;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtCompanyManager;
import com.mindata.ecserver.main.manager.PtUserPushCountManager;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.model.secondary.PtUserPushCount;
import com.mindata.ecserver.main.requestbody.PushBody;
import com.mindata.ecserver.main.requestbody.PushFailRequestBody;
import com.mindata.ecserver.main.requestbody.PushResultRequestBody;
import com.mindata.ecserver.main.service.PushFailResultService;
import com.mindata.ecserver.main.service.PushService;
import com.mindata.ecserver.main.service.PushSuccessResultService;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@RestController
@RequestMapping("/push")
public class PushController {
    @Resource
    private PushService pushService;
    @Resource
    private PtUserPushCountManager ptUserPushCountManager;
    @Resource
    private PushSuccessResultService pushSuccessResultService;
    @Resource
    private PushFailResultService pushFailResultService;
    @Resource
    private PtCompanyManager ptCompanyManager;


    private static final int MAX_SIZE = 50;

    /**
     * 推送指定的id集合到ec
     *
     * @param pushBody id集合
     * @return 结果
     */
    @PostMapping({"", "/"})
    @CheckEcAnnotation
    public BaseData push(@RequestBody PushBody pushBody) throws IOException {
        //没传跟进人，默认为自己
        if (pushBody.getFollowUserId() == null) {
            PtUser ptUser = ShiroKit.getCurrentUser();
            if (ptUser.getEcUserId() == null) {
                return ResultGenerator.genFailResult("用户没绑定EC账号");
            }
            pushBody.setFollowUserId(ptUser.getId());
        }
        List<Long> ids = pushBody.getIds();
        if (ids.size() > MAX_SIZE) {
            return ResultGenerator.genFailResult(ResultCode.PUSH_COUNT_TO_LARGE, "一次最多推送50条");
        }
        //检查被推送的用户每日上限
        PtUserPushCount userCount = ptUserPushCountManager.findCountByUserId(pushBody.getFollowUserId(), null);
        if (ids.size() + userCount.getPushedCount() > userCount.getThreshold()) {
            return ResultGenerator.genFailResult(ResultCode.PUSH_COUNT_BEYOND_TODAY_LIMIT, "已超出今日最大限制");
        }
        // 检查已推送的数量是否大于公司规定的推送数量
        Long companyId = ShiroKit.getCurrentUser().getCompanyId();
        if (ptUserPushCountManager.getPushedCountSum(companyId) > ptCompanyManager.findOne(companyId).getThreshold()) {
            return ResultGenerator.genFailResult(ResultCode.PUSH_COUNT_BEYOND_TODAY_LIMIT, "已超出今日公司规定最大限制");
        }
        return ResultGenerator.genSuccessResult(pushService.push(pushBody));
    }

    /**
     * 查看推送成功的历史
     *
     * @param pushResultRequestBody body
     * @return 结果
     */
    @GetMapping("/success")
    public BaseData get(@ModelAttribute PushResultRequestBody pushResultRequestBody) {
        return ResultGenerator.genSuccessResult(pushSuccessResultService.findByConditions(pushResultRequestBody));
    }

    /**
     * 查看推送失败的历史
     */
    @GetMapping("/failure")
    public BaseData get(@ModelAttribute PushFailRequestBody pushFailRequestBody) {
        return ResultGenerator.genSuccessResult(pushFailResultService.findByConditions(pushFailRequestBody));
    }

    /**
     * 查询某段时间内每天共有多少人推送了多少条数据
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 每天的聚合数据[{"date":2017-08-09, "total":203, "user":14}]
     */
    @GetMapping("/count")
    public BaseData getCount(String begin, String end) {
        return ResultGenerator.genSuccessResult(ptUserPushCountManager.findByPushDateTime(begin, end));
    }
}
