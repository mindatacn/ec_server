package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.manager.ec.CodeWebsiteManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
@RestController
@RequestMapping("/codeWeb")
public class CodeWebsiteController {
    @Resource
    private CodeWebsiteManager codeWebsiteManager;

    /**
     * 获取所有来源
     */
    @GetMapping("")
    public BaseData get() {
        return ResultGenerator.genSuccessResult(codeWebsiteManager.findAll());
    }
}
