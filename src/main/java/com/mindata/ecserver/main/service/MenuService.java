package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.event.MenuCrudEvent;
import com.mindata.ecserver.main.manager.PtMenuManager;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/3.
 */
@Service
public class MenuService extends BaseService {
    @Resource
    private PtMenuManager ptMenuManager;

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
}
