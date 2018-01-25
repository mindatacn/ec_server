package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.cache.RoleMenuCache;
import com.mindata.ecserver.main.event.MenuDeleteEvent;
import com.mindata.ecserver.main.event.RoleDeleteEvent;
import com.mindata.ecserver.main.event.RoleMenuChangeEvent;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.model.secondary.PtMenuRole;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.repository.secondary.PtMenuRepository;
import com.mindata.ecserver.main.repository.secondary.PtMenuRoleRepository;
import com.mindata.ecserver.main.requestbody.RoleMenuDto;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    private ApplicationEventPublisher eventPublisher;

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
        PtMenuRole menuRole = addOne(menuId, roleId);

        publishRoleEvent(CollectionUtil.newArrayList(roleId));
        return menuRole;
    }

    private PtMenuRole addOne(Long menuId, Long roleId) {
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

    public List<PtMenuRole> add(RoleMenuDto roleMenuDto) {
        return roleMenuDto.getMenuIds().stream().map(menuId -> addOne(menuId, roleMenuDto.getRoleId())).collect
                (Collectors.toList());
    }

    private void publishRoleEvent(List<Long> roleIds) {
        //发布角色菜单事件
        eventPublisher.publishEvent(new RoleMenuChangeEvent(roleIds));
    }

    /**
     * 删除全部，再添加全部
     *
     * @param roleMenuDto
     *         传来的
     * @return 集合
     */
    @Transactional(rollbackFor = Exception.class)
    public List<PtMenuRole> updateAll(RoleMenuDto roleMenuDto) {
        //删除所有
        delete(roleMenuDto);
        //添加所有
        List<PtMenuRole> ptMenuRoles = add(roleMenuDto);

        publishRoleEvent(CollectionUtil.newArrayList(roleMenuDto.getRoleId()));
        return ptMenuRoles;
    }

    /**
     * 删除某个role的menu
     *
     * @param menuId
     *         菜单id
     * @param roleId
     *         roleId
     */
    public void delete(Long menuId, Long roleId) {
        PtMenuRole ptMenuRole = ptMenuRoleRepository.findFirstByMenuIdAndRoleId(menuId, roleId);
        if (ptMenuRole != null) {
            ptMenuRoleRepository.delete(ptMenuRole);
        }
    }

    /**
     * 批量删除role和menu的对应关系
     *
     * @param roleMenuDto
     *         dto
     */
    public void delete(RoleMenuDto roleMenuDto) {
        roleMenuDto.getMenuIds().forEach(menuId -> delete(roleMenuDto.getRoleId(), menuId));
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
     * @param event
     *         event
     */
    @EventListener
    public void deleteMenuRoleByMenuId(MenuDeleteEvent event) {
        Long menuId = (Long) event.getSource();
        ptMenuRoleRepository.deleteByMenuId(menuId);
    }

    @EventListener
    public void deleteMenuRoleByRoleId(RoleDeleteEvent event) {
        Long roleId = (Long) event.getSource();
        ptMenuRoleRepository.deleteByRoleId(roleId);
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
