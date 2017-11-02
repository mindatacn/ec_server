package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.requestbody.PushResultRequestBody;
import com.mindata.ecserver.main.service.PushSuccessResultService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/24.
 * 数据检索历史
 */
@RestController
@RequestMapping("/pushResult")
public class PushResultController {
    @Resource
    private PushSuccessResultService pushSuccessResultService;

    /**
     * 获取各检索条件的数据集合
     *
     * @return 聚合信息
     */
    @PostMapping({"", "/"})
    public BaseData queryContactData(@RequestBody PushResultRequestBody pushResultRequestBody) {
        return ResultGenerator.genSuccessResult(pushSuccessResultService.findByConditions(pushResultRequestBody));
    }

    @GetMapping("/{id}")
    public BaseData queryOne(@PathVariable Integer id) {
        return ResultGenerator.genSuccessResult(pushSuccessResultService.findById(id));
    }
}

