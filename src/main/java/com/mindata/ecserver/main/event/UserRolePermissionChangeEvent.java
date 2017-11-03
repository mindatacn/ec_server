package com.mindata.ecserver.main.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 用户角色、权限发生变化时的回调事件
 */
public class UserRolePermissionChangeEvent extends ApplicationEvent {
    public UserRolePermissionChangeEvent(Object source) {
        super(source);
    }
}
