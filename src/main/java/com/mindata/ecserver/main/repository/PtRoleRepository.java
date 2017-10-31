package com.mindata.ecserver.main.repository;

import com.mindata.ecserver.main.model.PtRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtRoleRepository extends JpaRepository<PtRole, Integer> {
    PtRole findByName(String name);
}
