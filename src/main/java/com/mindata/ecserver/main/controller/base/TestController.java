package com.mindata.ecserver.main.controller.base;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.main.manager.EcVocationCodeManager;
import com.mindata.ecserver.main.manager.PtSearchConditionManager;
import com.mindata.ecserver.main.requestbody.PushBody;
import com.mindata.ecserver.main.service.ContactService;
import com.mindata.ecserver.main.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private EcVocationCodeManager ecVocationCodeManager;
    @Resource
    private PushService pushService;
    @Resource
    private ContactService contactService;
    @Resource
    private PtSearchConditionManager conditionManager;

    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    @RequestMapping("")
    public Object getVocation() {
        return ecVocationCodeManager.findAll();
    }

    @GetMapping("/search")
    public Object search() {
        Object object = conditionManager.find();
        return object;
    }

    @CheckEcAnnotation
    @RequestMapping("/push")
    public Object push() throws IOException {
        long beginId = 218302;
        long endId = 223301;

        long optUserId = 4;//侯学明
        long qizhi = 46;
        long zhao = 47;

        int count = 0;
        List<Long> ids = new ArrayList<>();

        PushBody pushBody = new PushBody();
        pushBody.setOptUserId(optUserId);
        pushBody.setFollowUserId(qizhi);
        for (Long i = beginId; i <= 220802; i++) {
            ids.add(i);
            if (count != 0 && count % 50 == 0) {
                pushBody.setIds(ids);
                pushService.push(pushBody);
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
        count = 0;
        pushBody.setFollowUserId(zhao);
        for (Long i = 220803L; i <= endId; i++) {
            ids.add(i);
            if (count != 0 && count % 50 == 0) {
                pushBody.setIds(ids);
                pushService.push(pushBody);
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
