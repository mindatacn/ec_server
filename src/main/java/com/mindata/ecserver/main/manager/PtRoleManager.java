package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.cache.UserRoleCache;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.model.secondary.PtMenuRole;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.model.secondary.PtUserRole;
import com.mindata.ecserver.main.repository.secondary.PtMenuRoleRepository;
import com.mindata.ecserver.main.repository.secondary.PtRoleRepository;
import com.mindata.ecserver.main.repository.secondary.PtUserRoleRepository;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class PtRoleManager {
    @Resource
    private PtRoleRepository ptRoleRepository;
    @Resource
    private PtUserRoleRepository ptUserRoleRepository;
    @Resource
    private UserRoleCache userRoleCache;
    @Resource
    private PtMenuRoleRepository ptMenuRoleRepository;

    /**
     * 查询用户的所有role
     *
     * @param userId
     *         userId
     * @return 集合
     */
    public List<PtRole> findByUserId(Long userId) {
        List<PtUserRole> userRoles = ptUserRoleRepository.findByUserId(userId);
        return userRoles.stream().map(userRole -> ptRoleRepository.findOne(userRole.getRoleId())).collect(Collectors
                .toList());
    }

    public PtRole findByRoleId(Long roleId) {
        return ptRoleRepository.findOne(roleId);
    }

    /**
     * 获取用户的roles
     *
     * @param user
     *         user
     * @return 角色集合
     */
    public List<PtRole> findRolesByUser(PtUser user) {
        //从缓存获取
        List<PtRole> roles = userRoleCache.findRolesByUserId(user.getId());
        if (CollectionUtil.isEmpty(roles)) {
            roles = findByUserId(user.getId());
            //放入缓存
            userRoleCache.saveUserRolesByUser(user.getId(), roles);
        }
        return roles;
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
        return menuRoles.stream().map(PtMenuRole::getRoleId).map(this::findByRoleId).collect(Collectors.toList());
    }

    public boolean isManager(Long userId) {
        //判断用户角色
        List<PtRole> roles = findByUserId(userId);
        for (PtRole ptRole : roles) {
            if (Constant.ROLE_MANAGER.equals(ptRole.getName())) {
                return true;
            }
        }
        return false;
    }

    public Long findIdByName(String name) {
        return ptRoleRepository.findByName(name).getId();
    }

    public List<PtRole> findAll() {
        return ptRoleRepository.findAll();
    }
}
