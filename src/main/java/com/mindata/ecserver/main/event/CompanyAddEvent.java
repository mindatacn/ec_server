package com.mindata.ecserver.main.event;

import com.mindata.ecserver.main.requestbody.CompanyBody;
import org.springframework.context.ApplicationEvent;

/**
 * 新增公司事件
 *
 * @author wuweifeng wrote on 2018/1/24.
 */
public class CompanyAddEvent extends ApplicationEvent {
    public CompanyAddEvent(CompanyBody companyBody) {
        super(companyBody);
    }
}
