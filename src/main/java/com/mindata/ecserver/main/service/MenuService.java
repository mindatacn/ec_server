package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.MenuCrudEvent;
import com.mindata.ecserver.main.manager.PtMenuManager;
import com.mindata.ecserver.main.manager.PtRoleManager;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/11/3.
 */
@Service
public class MenuService extends BaseService {
    @Resource
    private PtMenuManager ptMenuManager;
    @Resource
    private PtRoleManager ptRoleManager;

    /**
     * 添加菜单
     *
     * @param ptMenu
     *         菜单
     * @return 菜单
     */
    public PtMenu add(PtMenu ptMenu) {
        ptMenu.setCreateTime(CommonUtil.getNow());
        ptMenu.setUpdateTime(CommonUtil.getNow());
        //发布菜单事件
        PtMenu menu = ptMenuManager.save(ptMenu);
        eventPublisher.publishEvent(new MenuCrudEvent(ptMenu));
        return menu;
    }

    /**
     * 添加菜单
     *
     * @param ptMenu
     *         菜单
     * @return 菜单
     */
    public PtMenu update(PtMenu ptMenu) {
        ptMenu.setUpdateTime(CommonUtil.getNow());
        //发布菜单事件
        PtMenu menu = ptMenuManager.save(ptMenu);
        eventPublisher.publishEvent(new MenuCrudEvent(ptMenu));
        return menu;
    }

    public void delete(Integer id) {
        ptMenuManager.delete(id);
        //发布菜单事件
        eventPublisher.publishEvent(new MenuCrudEvent(null));
    }

    /**
     * 查询某个菜单的子菜单，默认0则查询所有一级菜单
     *
     * @param parentId
     *         父菜单id
     */
    public List<PtMenu> find(final Integer parentId) {
        int id = parentId == null ? 0 : parentId;
        List<PtRole> ptRoleList = ptRoleManager.findByUserId(ShiroKit.getCurrentUser().getId());
        //得到该用户所有菜单
        List<PtMenu> menuList = ptMenuManager.findAllByRoles(ptRoleList);
        return menuList.stream().filter(ptMenu -> ptMenu.getParentId() == id).sorted().collect(Collectors.toList());
    }
}
