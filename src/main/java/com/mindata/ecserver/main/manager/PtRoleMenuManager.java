package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.cache.RoleMenuCache;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.model.secondary.PtMenuRole;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.repository.secondary.PtMenuRepository;
import com.mindata.ecserver.main.repository.secondary.PtMenuRoleRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2018/1/11.
 */
@Component
public class PtRoleMenuManager {
    @Resource
    private PtMenuRoleRepository ptMenuRoleRepository;
    @Resource
    private PtRoleManager ptRoleManager;
    @Resource
    private RoleMenuCache roleMenuCache;
    @Resource
    private PtMenuRepository ptMenuRepository;

    /**
     * 给某个role赋值权限
     *
     * @param menuId
     *         menuId
     * @param roleId
     *         roleId
     * @return menuRole
     */
    public PtMenuRole add(Long menuId, Long roleId) {
        PtMenuRole ptMenuRole = ptMenuRoleRepository.findFirstByMenuIdAndRoleId(menuId, roleId);
        if (ptMenuRole == null) {
            ptMenuRole = new PtMenuRole();
            ptMenuRole.setMenuId(menuId);
            ptMenuRole.setRoleId(roleId);
            ptMenuRole.setCreateTime(CommonUtil.getNow());
            ptMenuRole.setUpdateTime(CommonUtil.getNow());
            ptMenuRole = ptMenuRoleRepository.save(ptMenuRole);
        }
        return ptMenuRole;
    }

    /**
     * 删除某个role的menu
     *
     * @param menuId
     *         菜单id
     * @param roleId
     *         roleId
     */
    public Integer delete(Long menuId, Long roleId) {
        return ptMenuRoleRepository.deleteByMenuIdAndRoleId(menuId, roleId);
    }

    public boolean checkExist(Long menuId, Long roleId) {
        return ptMenuRepository.exists(menuId) && ptRoleManager.exists(roleId);
    }

    /**
     * 查询拥有某个菜单的所有role
     *
     * @param menuId
     *         菜单id
     * @return role集合
     */
    public List<PtRole> findRolesByMenu(Long menuId) {
        List<PtMenuRole> menuRoles = ptMenuRoleRepository.findByMenuId(menuId);
        return menuRoles.stream().map(PtMenuRole::getRoleId).map(roleId -> ptRoleManager.findByRoleId(roleId)).collect
                (Collectors
                        .toList());
    }

    /**
     * 根据菜单id删除menuRole表的记录
     *
     * @param menuId
     *         menuId
     */
    public void deleteMenuRoleByMenuId(Long menuId) {
        ptMenuRoleRepository.deleteByMenuId(menuId);
    }

    public List<PtMenu> findByRoleId(Long roleId) {
        //读缓存
        List<PtMenu> menuList = roleMenuCache.findMenuByRoleId(roleId);
        if (menuList != null) {
            return menuList;
        }
        List<PtMenuRole> menuRoles = ptMenuRoleRepository.findByRoleId(roleId);
        menuList = menuRoles.stream().map(ptMenuRole -> ptMenuRepository.findOne(ptMenuRole.getMenuId())).collect
                (Collectors.toList());
        roleMenuCache.saveMenusByRoleId(roleId, menuList);
        return menuList;
    }

    public List<PtMenu> findAllMenuByRoles(List<PtRole> roles) {
        List<PtMenu> menus = new ArrayList<>();
        for (PtRole role : roles) {
            menus.addAll(findByRoleId(role.getId()));
        }
        return menus;
    }
}
