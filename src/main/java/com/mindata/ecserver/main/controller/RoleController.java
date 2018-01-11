package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.manager.PtRoleManager;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/1/11.
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private PtRoleManager ptRoleManager;

    /**
     * 获取所有角色
     */
    @GetMapping("")
    @RequiresRoles(Constant.ROLE_ADMIN)
    public BaseData get() {
        return ResultGenerator.genSuccessResult(ptRoleManager.findAll());
    }

}
