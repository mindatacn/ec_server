package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.model.secondary.PtUserRole;
import com.mindata.ecserver.main.repository.secondary.PtRoleRepository;
import com.mindata.ecserver.main.repository.secondary.PtUserRoleRepository;
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
