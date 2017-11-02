package com.mindata.ecserver.main.controller.base;

import com.mindata.ecserver.main.manager.EcVocationCodeManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private EcVocationCodeManager ecVocationCodeManager;

    @RequestMapping("")
    public Object getVocation() {
        return ecVocationCodeManager.findAll();
    }
}
