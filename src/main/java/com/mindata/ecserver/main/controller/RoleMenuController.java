package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.manager.PtRoleMenuManager;
import com.mindata.ecserver.main.requestbody.RoleMenuDto;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.DeleteMapping;
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
     * 给某个role添加菜单
     */
    @PostMapping("")
    @RequiresRoles(Constant.ROLE_ADMIN)
    public BaseData add(RoleMenuDto roleMenuDto) {
        if (roleMenuDto.getRoleId() == null || CollectionUtil.isEmpty(roleMenuDto.getMenuIds())) {
            return ResultGenerator.genFailResult("roleId和menuId都不能为空");
        }

        return ResultGenerator.genSuccessResult(ptRoleMenuManager.add(roleMenuDto));
    }

    /**
     * 给某个role删除菜单
     */
    @DeleteMapping("")
    @RequiresRoles(Constant.ROLE_ADMIN)
    public BaseData delete(Long roleId, Long menuId) {
        if (roleId == null || menuId == null) {
            return ResultGenerator.genFailResult("roleId和menuId都不能为空");
        }

        return ResultGenerator.genSuccessResult(ptRoleMenuManager.delete(menuId, roleId));
    }
}
