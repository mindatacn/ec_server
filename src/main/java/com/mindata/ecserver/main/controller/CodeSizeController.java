package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.manager.CodeSizeManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
@RestController
@RequestMapping("/codeSize")
public class CodeSizeController {
    @Resource
    private CodeSizeManager codeSizeManager;

    /**
     * 获取所有规模
     */
    @GetMapping("")
    public BaseData get() {
        return ResultGenerator.genSuccessResult(codeSizeManager.findAll());
    }
}
