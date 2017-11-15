package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 菜单
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    /**
     * 获取自己的菜单
     *
     * @param parentId
     *         父菜单id
     * @return 菜单结果
     */
    @GetMapping("")
    public BaseData get(Long parentId) {
        return ResultGenerator.genSuccessResult(menuService.find(parentId));
    }

    @PostMapping("")
    @RequiresRoles(Constant.ROLE_ADMIN)
    public BaseData add(@ModelAttribute PtMenu ptMenu) {
        return ResultGenerator.genSuccessResult(menuService.add(ptMenu));
    }
}
