package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.manager.PtRoleManager;
import com.mindata.ecserver.main.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/1/11.
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private PtRoleManager ptRoleManager;
    @Resource
    private RoleService roleService;

    /**
     * 获取所有角色
     */
    @GetMapping("")
    public BaseData get() {
        return ResultGenerator.genSuccessResult(ptRoleManager.findAll());
    }

    /**
     * 添加角色
     *
     * @param name
     *         名字
     * @return 结果
     */
    @PostMapping("")
    public BaseData add(String name, String sign) {
        return ResultGenerator.genSuccessResult(roleService.add(name, sign));
    }

    /**
     * 修改角色
     *
     * @param name
     *         名字
     * @return 结果
     */
    @PutMapping("/{id}")
    public BaseData update(@PathVariable Long id, String name, String sign) {
        return ResultGenerator.genSuccessResult(roleService.update(id, name, sign));
    }

    /**
     * 删除角色
     *
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public BaseData delete(@PathVariable Long id) {
        boolean success = roleService.delete(id);
        if (success) {
            return ResultGenerator.genSuccessResult("删除成功");
        }
        return ResultGenerator.genFailResult("请先删除所有该角色的用户对应关系");

    }

}
