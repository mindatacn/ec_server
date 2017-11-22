package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.requestbody.ContactRequestBody;
import com.mindata.ecserver.main.service.SearchConditionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 搜索条件记录功能
 *
 * @author wuweifeng wrote on 2017/11/21.
 */
@RestController
@RequestMapping("/searchCondition")
public class SearchConditionController {
    @Resource
    private SearchConditionService searchConditionService;

    /**
     * 获取各检索条件的数据集合
     *
     * @return 聚合信息
     */
    @PostMapping({"", "/"})
    public BaseData queryContactData(@RequestBody ContactRequestBody contactRequestBody) {
        return ResultGenerator.genSuccessResult(searchConditionService.add(contactRequestBody));
    }


}
