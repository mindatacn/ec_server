package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtUserRoleRepository extends JpaRepository<PtUserRole, Long> {
    List<PtUserRole> findByUserId(Long userId);

    PtUserRole findByUserIdAndRoleId(Long userId, Long roleId);
}
