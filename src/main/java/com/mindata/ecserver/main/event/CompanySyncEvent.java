package com.mindata.ecserver.main.event;

import org.springframework.context.ApplicationEvent;

/**
 * 从EC同步公司用的event
 *
 * @author wuweifeng wrote on 2017/11/15.
 */
public class CompanySyncEvent extends ApplicationEvent {
    public CompanySyncEvent(Boolean source) {
        super(source);
    }
}
