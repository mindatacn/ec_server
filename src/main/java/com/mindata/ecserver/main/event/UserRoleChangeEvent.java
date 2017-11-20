package com.mindata.ecserver.main.event;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 用户角色发生变化时的回调事件
 */
public class UserRoleChangeEvent extends ApplicationEvent {
    public UserRoleChangeEvent(List<Long> userIds) {
        super(userIds);
    }
}
