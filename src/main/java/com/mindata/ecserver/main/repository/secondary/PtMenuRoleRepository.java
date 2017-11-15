package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtMenuRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtMenuRoleRepository extends JpaRepository<PtMenuRole, Long> {
    /**
     * 根据role查询所有的菜单
     *
     * @param roleId
     *         role
     * @return 集合
     */
    List<PtMenuRole> findByRoleId(Long roleId);
}
