package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.PtMenu;
import com.mindata.ecserver.main.model.PtMenuRole;
import com.mindata.ecserver.main.model.PtRole;
import com.mindata.ecserver.main.repository.PtMenuRepository;
import com.mindata.ecserver.main.repository.PtMenuRoleRepository;
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

    public List<PtMenu> findByRoleId(int roleId) {
        List<PtMenuRole> menuRoles = ptMenuRoleRepository.findByRoleId(roleId);
        return menuRoles.stream().map(ptMenuRole -> ptMenuRepository.findOne(ptMenuRole.getMenuId())).collect(Collectors
                .toList());
    }

    public List<PtMenu> findAllByRoles(List<PtRole> roles) {
        List<PtMenu> menus = new ArrayList<>();
        for (PtRole role : roles) {
            menus.addAll(findByRoleId(role.getId()));
        }
        return menus;
    }
}
