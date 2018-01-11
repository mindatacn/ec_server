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

    /**
     * 查询某个menu的所有role
     *
     * @param menuId
     *         menuId
     * @return 集合
     */
    List<PtMenuRole> findByMenuId(Long menuId);

    /**
     * 删除某个菜单的所有记录
     *
     * @param menuId
     *         菜单id
     * @return 删除的数量
     */
    Integer deleteByMenuId(Long menuId);

    /**
     * 查询某个对应关系是否存在
     *
     * @param menuId
     *         菜单id
     * @param roleId
     *         roleId
     * @return menuRole
     */
    PtMenuRole findFirstByMenuIdAndRoleId(Long menuId, Long roleId);
}
