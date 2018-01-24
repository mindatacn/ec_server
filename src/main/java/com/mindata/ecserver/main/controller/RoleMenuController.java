package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.manager.PtRoleMenuManager;
import com.mindata.ecserver.main.requestbody.RoleMenuDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色权限的controller
 *
 * @author wuweifeng wrote on 2018/1/11.
 */
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {
    @Resource
    private PtRoleMenuManager ptRoleMenuManager;

    /**
     * 修改某个role的菜单
     */
    @PostMapping("")
    public BaseData update(RoleMenuDto roleMenuDto) {
        if (roleMenuDto.getRoleId() == null) {
            return ResultGenerator.genFailResult("roleId不能为空");
        }

        return ResultGenerator.genSuccessResult(ptRoleMenuManager.add(roleMenuDto));
    }

    /**
     * 查询角色的所有菜单
     * @param roleId
     * roleId
     * @return
     * 结果
     */
    @GetMapping("")
    public BaseData find(Long roleId) {
        return ResultGenerator.genSuccessResult(ptRoleMenuManager.findByRoleId(roleId));
    }
}
