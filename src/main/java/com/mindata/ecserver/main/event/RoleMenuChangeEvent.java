package com.mindata.ecserver.main.event;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 角色权限发生变化时的回调事件
 */
public class RoleMenuChangeEvent extends ApplicationEvent {
    public RoleMenuChangeEvent(List<Long> roleIds) {
        super(roleIds);
    }
}
