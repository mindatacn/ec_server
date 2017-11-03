package com.mindata.ecserver.main.controller.base;

import com.mindata.ecserver.main.manager.EcVocationCodeManager;
import com.mindata.ecserver.main.requestbody.PushBody;
import com.mindata.ecserver.main.service.ContactService;
import com.mindata.ecserver.main.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    @RequestMapping("")
    public Object getVocation() {
        return ecVocationCodeManager.findAll();
    }

    @RequestMapping("/push")
    public Object push() throws IOException {
        int beginId = 218302;
        int endId = 223301;

        long optUserId = 5799794;//侯学明
        long qizhi = 46;
        long zhao = 47;

        int count = 0;
        List<Integer> ids = new ArrayList<>();

        PushBody pushBody = new PushBody();
        pushBody.setOptUserId(optUserId);
        pushBody.setFollowUserId(qizhi);
        for (int i = beginId; i <= 220802; i++) {
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
        for (int i = 220803; i <= endId; i++) {
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
