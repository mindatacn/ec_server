package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.manager.PtDepartmentManager;
import com.mindata.ecserver.main.requestbody.DepartmentBody;
import com.mindata.ecserver.main.service.DepartmentService;
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
    @Resource
    private PtDepartmentManager ptDepartmentManager;

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

    /**
     * 添加部门
     *
     * @param departmentBody
     *         departmentBody
     * @return 结果
     */
    @PostMapping("")
    public BaseData add(@ModelAttribute DepartmentBody departmentBody) {
        if (!departmentService.isManager()) {
            return ResultGenerator.genFailResult("只有公司管理员有权限操作");
        }
        return ResultGenerator.genSuccessResult(ptDepartmentManager.add(departmentBody));
    }

    /**
     * 修改部门
     *
     * @param departmentBody
     *         departmentBody
     * @return 结果
     */
    @PutMapping("")
    public BaseData update(@ModelAttribute DepartmentBody departmentBody) {
        if (!departmentService.isManager()) {
            return ResultGenerator.genFailResult("只有公司管理员有权限操作");
        }
        return ResultGenerator.genSuccessResult(ptDepartmentManager.update(departmentBody));
    }

    /**
     * 删除部门
     *
     * @param id
     *         id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public BaseData delete(@PathVariable Long id) {
        if (!departmentService.isManager()) {
            return ResultGenerator.genFailResult("只有公司管理员有权限操作");
        }
        if (!departmentService.isDeptEmpty(id)) {
            return ResultGenerator.genFailResult("该部门还有未删除的员工");
        }
        ptDepartmentManager.delete(id);
        return ResultGenerator.genSuccessResult("删除成功");
    }
}
