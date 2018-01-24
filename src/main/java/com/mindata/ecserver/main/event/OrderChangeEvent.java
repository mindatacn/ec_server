package com.mindata.ecserver.main.event;

import org.springframework.context.ApplicationEvent;

/**
 * 订单创建、修改、删除事件
 *
 * @author wuweifeng wrote on 2018/1/24.
 */
public class OrderChangeEvent extends ApplicationEvent {

    public OrderChangeEvent(Long companyId) {
        super(companyId);
    }
}
