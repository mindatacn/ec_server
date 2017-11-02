package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.service.DepartmentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
     */
    @GetMapping("")
    @RequiresRoles(value = {Constant.ROLE_MANAGER, Constant.ROLE_ADMIN}, logical = Logical.OR)
    public BaseData find(@PageableDefault(sort = {"sort", "id"}, direction = Sort.Direction.DESC) Pageable pageable,
                         String name) {
        return ResultGenerator.genSuccessResult(departmentService.findByName(name, pageable));
    }


}
