package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtUserRoleRepository extends JpaRepository<PtUserRole, Long> {
    /**
     * 查询某用户的所有role
     *
     * @param userId
     *         userId
     * @return 集合
     */
    List<PtUserRole> findByUserId(Long userId);

    PtUserRole findByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据roleId查询
     *
     * @param roleId
     *         roleId
     * @return 集合
     */
    List<PtUserRole> findByRoleId(Long roleId);

    /**
     * 根据多个roleid查询
     * @param ids ids
     * @return List
     */
    List<PtUserRole> findByRoleIdIn(List<Long> ids);
}
