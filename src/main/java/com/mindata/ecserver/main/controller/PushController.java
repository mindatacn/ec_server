package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultCode;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.manager.PtUserPushCountManager;
import com.mindata.ecserver.main.requestbody.PushBody;
import com.mindata.ecserver.main.requestbody.PushFailRequestBody;
import com.mindata.ecserver.main.requestbody.PushResultRequestBody;
import com.mindata.ecserver.main.service.PushFailResultService;
import com.mindata.ecserver.main.service.PushService;
import com.mindata.ecserver.main.service.PushSuccessResultService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@RestController
@RequestMapping("/push")
public class PushController {
    @Resource
    private PushService pushService;

    @Resource
    private PushSuccessResultService pushSuccessResultService;
    @Resource
    private PushFailResultService pushFailResultService;
    @Resource
    private PtUserPushCountManager ptUserPushCountManager;

    /**
     * 推送指定的id集合到ec
     *
     * @param pushBody
     *         id集合
     * @return 结果
     */
    @PostMapping({"", "/"})
    @CheckEcAnnotation
    public BaseData push(@RequestBody PushBody pushBody) throws IOException {
        Map map = pushService.pushCheck(pushBody);
        if (map.get("resultCode") != ResultCode.SUCCESS) {
            return ResultGenerator.genFailResult((ResultCode) map.get("resultCode"), map.get("msg").toString());
        }
        return ResultGenerator.genSuccessResult(pushService.push(pushBody));
    }

    /**
     * 查看推送成功的历史
     *
     * @param pushResultRequestBody
     *         body
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
     * (该接口已废弃)
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 每天的聚合数据[{"date":2017-08-09, "total":203, "user":14}]
     */
    @GetMapping("/count")
    @Deprecated
    public BaseData getCount(String begin, String end) {
        return ResultGenerator.genSuccessResult(ptUserPushCountManager.findByPushDateTime(begin, end));
    }
}
