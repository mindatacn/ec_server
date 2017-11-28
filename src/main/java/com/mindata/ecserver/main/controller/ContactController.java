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
        return ResultGenerator.genSuccessResult(contactService.findByStateAndConditions(0, contactRequestBody));
    }

    @GetMapping("/{id}")
    public BaseData queryOne(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(contactService.findById(id));
    }

    /**
     * 按省份来查询不同省份的线索数据，查询某省按城市分组聚合的数据，查询某城市按行业聚合的数据
     * 共3个接口
     *
     * @return 按省份区分后的聚合分组数据
     */
    @GetMapping("/province")
    public BaseData findCountByProvince(){
        return ResultGenerator.genSuccessResult(contactService.findCountByProvince(null, null));
    }

    @GetMapping("/province/{province}")
    public BaseData findCountByProvince(@PathVariable Integer province) {
        return ResultGenerator.genSuccessResult(contactService.findCountByProvince(province, null));
    }

    @GetMapping("/province/{province}/{city}")
    public BaseData findCountByProvince(@PathVariable Integer province, @PathVariable Integer city) {
        return ResultGenerator.genSuccessResult(contactService.findCountByProvince(province, city));
    }

    /**
     * 查询某段时间每天的新增
     *
     * @return 结果
     */
    @GetMapping("/dayCount")
    public BaseData findCountByDay(String begin, String end) {
        return ResultGenerator.genSuccessResult(contactService.findCountByDateBetween(begin, end));
    }
}

