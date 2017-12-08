package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/12/8.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;


    /**
     * 查询某段时间的客户意向率、有效沟通量
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 结果
     */
    @GetMapping("/saleState")
    public BaseData saleState(String begin, String end) {
        return ResultGenerator.genSuccessResult(customerService.analyzeSaleState(begin, end));
    }
}
