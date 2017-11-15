package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.service.CustomerTagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@RestController
@RequestMapping("/tag")
public class TestController {
    @Resource
    private CustomerTagService customerTagService;

    @PostMapping("")
    public BaseData test() throws IOException {
        return ResultGenerator.genSuccessResult(customerTagService.syncFromEcToDb());
    }
}
