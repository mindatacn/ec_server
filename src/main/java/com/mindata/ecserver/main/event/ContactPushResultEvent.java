package com.mindata.ecserver.main.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wuweifeng wrote on 2017/11/1.
 */
public class ContactPushResultEvent extends ApplicationEvent {
    public ContactPushResultEvent(Object source) {
        super(source);
    }
}
