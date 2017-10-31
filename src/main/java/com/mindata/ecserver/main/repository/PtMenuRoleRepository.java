package com.mindata.ecserver.main.repository;

import com.mindata.ecserver.main.model.PtMenuRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtMenuRoleRepository extends JpaRepository<PtMenuRole, Integer> {
    List<PtMenuRole> findByRoleId(Integer roleId);
}
