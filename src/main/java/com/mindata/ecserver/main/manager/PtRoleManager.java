package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.cache.UserRoleCache;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.model.secondary.PtUserRole;
import com.mindata.ecserver.main.repository.secondary.PtRoleRepository;
import com.mindata.ecserver.main.repository.secondary.PtUserRoleRepository;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
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
    private PtUserManager ptUserManager;

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
     * 判断用户的角色
     *
     * @param ptUser
     *         用户
     * @return 角色名
     */
    public String getRoleStr(PtUser ptUser) {
        //判断用户角色
        List<PtRole> roles = findRolesByUser(ptUser);
        Set<String> roleNames = roles.stream().map(PtRole::getName).collect(Collectors.toSet());
        if (roleNames.contains(Constant.ROLE_ADMIN)) {
            return Constant.ROLE_ADMIN;
        }
        if (roleNames.contains(Constant.ROLE_MANAGER)) {
            return Constant.ROLE_MANAGER;
        }
        if (roleNames.contains(Constant.ROLE_LEADER)) {
            return Constant.ROLE_LEADER;
        }
        return Constant.ROLE_USER;
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
     * 判断是否是管理员
     *
     * @param userId
     *         用户id
     * @return 是否是管理员
     */
    public boolean isManager(Long userId) {
        PtUser ptUser = ptUserManager.findByUserId(userId);
        return Constant.ROLE_MANAGER.equals(getRoleStr(ptUser));
    }

    public Long findIdByName(String name) {
        return ptRoleRepository.findByName(name).getId();
    }

    public List<PtRole> findAll() {
        return ptRoleRepository.findAll();
    }

    public boolean exists(Long id) {
        return ptRoleRepository.exists(id);
    }
}
