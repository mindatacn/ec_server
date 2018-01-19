package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.service.DepartmentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    /**
     * 根据名称查找部门
     *
     * @param name
     * 部门名
     * @return
     * 部门集合
     */
    @GetMapping("")
    public BaseData find(@PageableDefault(sort = {"sort", "id"}, direction = Sort.Direction.DESC) Pageable pageable,
                         String name, Long companyId) {
        return ResultGenerator.genSuccessResult(departmentService.findByName(companyId, name, pageable));
    }


}
