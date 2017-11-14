package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultCode;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtUserPushThresholdManager;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.model.secondary.PtUserPushCount;
import com.mindata.ecserver.main.requestbody.PushBody;
import com.mindata.ecserver.main.requestbody.PushResultRequestBody;
import com.mindata.ecserver.main.service.PushService;
import com.mindata.ecserver.main.service.PushSuccessResultService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
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
    private PtUserPushThresholdManager ptUserPushThresholdManager;
    @Resource
    private PushSuccessResultService pushSuccessResultService;

    private static final int MAX_SIZE = 50;
    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    /**
     * 推送指定的id集合到ec
     *
     * @param pushBody
     *         id集合
     * @return 结果
     */
    @PostMapping({"", "/"})
    @CheckEcAnnotation
    @RequiresRoles(value = {Constant.ROLE_USER, Constant.ROLE_LEADER}, logical = Logical.OR)
    public BaseData push(@RequestBody PushBody pushBody) throws IOException {
        //没传跟进人，默认为自己
        if (pushBody.getFollowUserId() == null) {
            PtUser ptUser = ShiroKit.getCurrentUser();
            if (ptUser.getEcUserId() == null) {
                return ResultGenerator.genFailResult("用户没绑定EC账号");
            }
            pushBody.setFollowUserId((long) ptUser.getId());
        }
        List<Integer> ids = pushBody.getIds();
        if (ids.size() > MAX_SIZE) {
            return ResultGenerator.genFailResult(ResultCode.PUSH_COUNT_TO_LARGE, "一次最多推送50条");
        }
        //检查被推送的用户每日上限
        PtUserPushCount userCount = ptUserPushThresholdManager.findCountByUserId(pushBody.getFollowUserId().intValue()
                , null);
        if (ids.size() + userCount.getPushedCount() > userCount.getThreshold()) {
            return ResultGenerator.genFailResult(ResultCode.PUSH_COUNT_BEYOND_TODAY_LIMIT, "已超出今日最大限制");
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
    public BaseData get(PushResultRequestBody pushResultRequestBody) {
        return ResultGenerator.genSuccessResult(pushSuccessResultService.findByConditions(pushResultRequestBody));
    }


    @CheckEcAnnotation
    @RequestMapping("/push")
    public Object push() throws IOException, InterruptedException {
        int beginId = 218302;
        int endId = 223301;
        long optUserId = 4;//侯学明
        long qizhi = 46;
        long zhao = 47;

        int count = 1;
        List<Integer> ids = new ArrayList<>();

        PushBody pushBody = new PushBody();
        pushBody.setOptUserId(optUserId);
        pushBody.setFollowUserId(qizhi);
        for (int i = beginId; i <= 220802; i++) {
            ids.add(i);
            if (count != 0 && count % 50 == 0) {
                pushBody.setIds(ids);
                pushService.push(pushBody);
                Thread.sleep(300);
                ids.clear();
                logger.info("已推送50个");
            }
            count++;
        }
        if (ids.size() > 0) {
            pushService.push(pushBody);
            logger.info("给qizhi的推送完毕");
        }

        ids.clear();
        count = 1;
        pushBody.setFollowUserId(zhao);
        for (int i = 220803; i <= endId; i++) {
            ids.add(i);
            if (count != 0 && count % 50 == 0) {
                pushBody.setIds(ids);
                pushService.push(pushBody);
                Thread.sleep(300);
                ids.clear();
                logger.info("已推送50个");
            }
            count++;
        }
        if (ids.size() > 0) {
            pushService.push(pushBody);
            logger.info("给qizhi的推送完毕");
        }
        return "success";
    }
}
