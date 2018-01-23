package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.cache.UserRoleCache;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.model.secondary.PtUserRole;
import com.mindata.ecserver.main.repository.secondary.PtRoleRepository;
import com.mindata.ecserver.main.repository.secondary.PtUserRoleRepository;
import com.mindata.ecserver.util.CommonUtil;
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
     * 添加一个role
     *
     * @param ptRole
     *         ptRole
     * @return ptRole
     */
    public PtRole add(PtRole ptRole) {
        ptRole.setCreateTime(CommonUtil.getNow());
        ptRole.setUpdateTime(CommonUtil.getNow());
        return ptRoleRepository.save(ptRole);
    }

    public PtRole update(PtRole ptRole) {
        ptRole.setUpdateTime(CommonUtil.getNow());
        return ptRoleRepository.save(ptRole);
    }

    /**
     * 删除角色
     *
     * @param roleId
     *         roleId
     */
    public void delete(Long roleId) {
        ptRoleRepository.delete(roleId);
    }

    /**
     * 判断用户的角色
     *
     * @param ptUser
     *         用户
     * @return 角色名
     */
    public List<PtRole> getRolesByUser(PtUser ptUser) {
        //判断用户角色
        return findRolesByUser(ptUser);
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

    public List<PtRole> findRolesByUserId(Long userId) {
        PtUser ptUser = ptUserManager.findByUserId(userId);
        return findRolesByUser(ptUser);
    }


    /**
     * 判断是否是公司管理员
     *
     * @param userId
     *         用户id
     * @return 是否是管理员
     */
    public boolean isManager(Long userId) {
        List<PtRole> ptRoles = findRolesByUserId(userId);
        return ptRoles.stream().map(PtRole::getCompanyId).collect(Collectors.toSet()).contains(Constant
                .MANAGER_COMPANY_ID);
    }

    /**
     * 是否是超管
     *
     * @param userId
     *         userId
     * @return 是否
     */
    public boolean isAdmin(Long userId) {
        List<PtRole> ptRoles = findRolesByUserId(userId);
        return ptRoles.stream().map(PtRole::getName).collect(Collectors.toSet()).contains(Constant.ROLE_ADMIN);
    }

    public Long findIdByName(String name) {
        return ptRoleRepository.findByName(name).getId();
    }

    /**
     * 查询所有role
     * @return
     * role集合
     */
    public List<PtRole> findAll() {
        PtUser ptUser = ShiroKit.getCurrentUser();

        return ptRoleRepository.findByCompanyId(ptUser.getCompanyId());
    }

    public boolean exists(Long id) {
        return ptRoleRepository.exists(id);
    }

    /**
     * 查询公司管理员
     */
    public List<Long> findManager(){
       List<PtRole> ptRoles =  ptRoleRepository.findByCompanyId(0L);
        return ptRoles.stream().map(role -> role.getId()).collect(Collectors.toList());
    }
}
