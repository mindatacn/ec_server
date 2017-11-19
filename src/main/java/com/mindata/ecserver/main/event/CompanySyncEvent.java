package com.mindata.ecserver.main.event;

import org.springframework.context.ApplicationEvent;

/**
 * 从EC同步公司用的event
 *
 * @author wuweifeng wrote on 2017/11/15.
 */
public class CompanySyncEvent extends ApplicationEvent {
    /**
     * 是否是强制同步，默认不强制，即不覆盖更新
     *
     * @param force
     *         是否强制
     */
    public CompanySyncEvent(Boolean force) {
        super(force);
    }
}
