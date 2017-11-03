package com.mindata.ecserver.main.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 菜单增删改查事件，需要清除所有与菜单相关的缓存
 */
public class MenuCrudEvent extends ApplicationEvent {
    public MenuCrudEvent(Object source) {
        super(source);
    }
}
