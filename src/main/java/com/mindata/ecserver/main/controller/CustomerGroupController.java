package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.ec.model.request.CustomerGroupRequest;
import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.service.CustomerGroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@RestController
@RequestMapping("/customerGroup")
public class CustomerGroupController {
    @Resource
    private CustomerGroupService customerGroupService;

    @PostMapping("")
    @CheckEcAnnotation
    public BaseData getCustomerGroup() throws IOException {
        return ResultGenerator.genSuccessResult(customerGroupService.getCustomerGroup());
    }

}
