package com.mindata.ecserver.main.event;

import org.springframework.context.ApplicationEvent;

/**
 * 菜单被删除事件
 *
 * @author wuweifeng wrote on 2018/1/16.
 */
public class MenuDeleteEvent extends ApplicationEvent {
    public MenuDeleteEvent(Long menuId) {
        super(menuId);
    }
}
