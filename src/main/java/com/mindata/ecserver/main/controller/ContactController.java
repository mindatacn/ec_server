package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.requestbody.ContactRequestBody;
import com.mindata.ecserver.main.service.ContactService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/24.
 * 数据检索
 */
@RestController
@RequestMapping("/contact")
public class ContactController {
    @Resource
    private ContactService contactService;

    /**
     * 获取各检索条件的数据集合
     *
     * @return 聚合信息
     */
    @PostMapping({"", "/"})
    public BaseData queryContactData(@RequestBody ContactRequestBody contactRequestBody) {
        return ResultGenerator.genSuccessResult(contactService.findByPushedAndConditions(false, contactRequestBody));
    }

    @GetMapping("/{id}")
    public BaseData queryOne(@PathVariable Integer id) {
        return ResultGenerator.genSuccessResult(contactService.findById(id));
    }
}

