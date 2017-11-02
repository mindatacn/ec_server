package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.manager.EcCodeVocationTagManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@RestController
@RequestMapping("/vocation")
public class VocationController {

    @Resource
    private EcCodeVocationTagManager ecCodeVocationTagManager;

    /**
     * 查询所有的行业
     *
     * @return 集合
     */
    @GetMapping({"", "/"})
    public BaseData find() {
        return ResultGenerator.genSuccessResult(ecCodeVocationTagManager.findAll());
    }
}
