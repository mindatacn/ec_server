package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtUserRole;
import com.mindata.ecserver.main.repository.secondary.PtUserRoleRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/10/31.
 */
@Service
public class PtUserRoleManager {
    @Resource
    private PtUserRoleRepository ptUserRoleRepository;

    /**
     * 添加一个role和user的对应关系
     */
    public PtUserRole add(Long userId, Long roleId) {
        PtUserRole ptUserRole = ptUserRoleRepository.findByUserIdAndRoleId(userId, roleId);
        if (ptUserRole != null) {
            return ptUserRole;
        }
        ptUserRole = new PtUserRole();
        ptUserRole.setRoleId(roleId);
        ptUserRole.setUserId(userId);
        ptUserRole.setCreateTime(CommonUtil.getNow());
        ptUserRole.setUpdateTime(CommonUtil.getNow());
        return ptUserRoleRepository.save(ptUserRole);
    }

    /**
     * 根据roleId查询
     *
     * @param roleId
     *         roleId
     * @return 集合
     */
    public List<PtUserRole> findByRoleId(Long roleId) {
        return ptUserRoleRepository.findByRoleId(roleId);
    }

    /**
     * 根据多个roleId查询对应的userId
     * @param roleIds roleIds
     * @return List
     */
    public List<Long> findUserIdsByRoleId(List<Long> roleIds){
        List<PtUserRole> ptUserRoles = ptUserRoleRepository.findByRoleIdIn(roleIds);
        return ptUserRoles.stream().map(role -> role.getUserId()).collect(Collectors.toList());
    }

    /**
     * 根据userId查找角色
     *
     * @param userId
     *         userId
     * @return List
     */
    public List<PtUserRole> findByUserId(Long userId) {
        return ptUserRoleRepository.findByUserId(userId);
    }

    /**
     * 修改
     *
     * @param ptUserRole
     *         ptUserRole
     */
    public void update(PtUserRole ptUserRole) {
        ptUserRoleRepository.save(ptUserRole);
    }
}
