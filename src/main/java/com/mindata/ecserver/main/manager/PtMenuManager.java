package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.cache.RoleMenuCache;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.model.secondary.PtMenuRole;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.repository.secondary.PtMenuRepository;
import com.mindata.ecserver.main.repository.secondary.PtMenuRoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class PtMenuManager {
    @Resource
    private PtMenuRepository ptMenuRepository;
    @Resource
    private PtMenuRoleRepository ptMenuRoleRepository;
    @Resource
    private RoleMenuCache roleMenuCache;

    /**
     * 添加一个菜单
     *
     * @return 菜单
     */
    public PtMenu save(PtMenu ptMenu) {
        return ptMenuRepository.save(ptMenu);
    }

    public void delete(Integer id) {
        ptMenuRepository.delete(id);
    }

    public List<PtMenu> findByRoleId(int roleId) {
        //读缓存
        List<PtMenu> menuList = roleMenuCache.findMenuByRoleId(roleId);
        if (menuList != null) {
            return menuList;
        }
        List<PtMenuRole> menuRoles = ptMenuRoleRepository.findByRoleId(roleId);
        menuList = menuRoles.stream().map(ptMenuRole -> ptMenuRepository.findOne(ptMenuRole.getMenuId())).collect
                (Collectors
                .toList());
        roleMenuCache.setMenuByRoleId(roleId, menuList);
        return menuList;
    }

    public List<PtMenu> findAllByRoles(List<PtRole> roles) {
        List<PtMenu> menus = new ArrayList<>();
        for (PtRole role : roles) {
            menus.addAll(findByRoleId(role.getId()));
        }
        return menus;
    }
}
