package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.RoleMenuChangeEvent;
import com.mindata.ecserver.main.manager.PtMenuManager;
import com.mindata.ecserver.main.manager.PtRoleManager;
import com.mindata.ecserver.main.manager.PtRoleMenuManager;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/11/3.
 */
@Service
public class MenuService extends BaseService {
    @Resource
    private PtMenuManager ptMenuManager;
    @Resource
    private PtRoleManager ptRoleManager;
    @Resource
    private PtRoleMenuManager ptRoleMenuManager;

    /**
     * 添加菜单
     *
     * @param ptMenu
     *         菜单
     * @return 菜单
     */
    public PtMenu add(PtMenu ptMenu) {
        ptMenu.setCreateTime(CommonUtil.getNow());
        ptMenu.setUpdateTime(CommonUtil.getNow());

        return ptMenuManager.save(ptMenu);
    }

    /**
     * 添加菜单
     *
     * @param ptMenu
     *         菜单
     * @return 菜单
     */
    public PtMenu update(PtMenu ptMenu) {
        ptMenu.setUpdateTime(CommonUtil.getNow());
        //发布菜单事件
        PtMenu menu = ptMenuManager.save(ptMenu);

        notifyMenuChangeEvent(ptMenu.getId());
        return menu;
    }

    /**
     * 删除菜单
     *
     * @param id
     *         菜单id
     */
    public void delete(Long id) {
        ptMenuManager.delete(id);
        notifyMenuChangeEvent(id);
        //删除MenuRole中关于该menu的记录
        ptRoleMenuManager.deleteMenuRoleByMenuId(id);
    }

    /**
     * 当菜单信息变更时，通知所有拥有该菜单的role
     *
     * @param menuId
     *         菜单id
     */
    private void notifyMenuChangeEvent(Long menuId) {
        //查询有该菜单的所有role
        List<PtRole> roles = ptRoleMenuManager.findRolesByMenu(menuId);

        //发布菜单事件
        eventPublisher.publishEvent(new RoleMenuChangeEvent(roles.stream().map(PtRole::getId).collect(Collectors
                .toList())));
    }

    /**
     * 查询某个菜单的子菜单，默认0则查询所有一级菜单
     *
     * @param parentId
     *         父菜单id
     */
    public List<PtMenu> find(final Long parentId) {
        long id = parentId == null ? 0 : parentId;
        List<PtRole> ptRoleList = ptRoleManager.findByUserId(ShiroKit.getCurrentUser().getId());
        //得到该用户所有菜单
        List<PtMenu> menuList = ptRoleMenuManager.findAllMenuByRoles(ptRoleList);
        return menuList.stream().filter(ptMenu -> ptMenu.getParentId() == id).sorted().collect(Collectors.toList());
    }
}
