package com.mindata.ecserver.main.vo;

import com.mindata.ecserver.ec.model.response.CustomerCreateDataBean;
import com.mindata.ecserver.main.requestbody.PushBody;

/**
 * @author wuweifeng wrote on 2017/11/1.
 * 将推送后的结果封装一下，发成event
 */
public class PushResultVO {
    private CustomerCreateDataBean customerCreateDataBean;
    private PushBody pushBody;
    private Long optUserId;

    public PushResultVO() {
    }

    public PushResultVO(CustomerCreateDataBean customerCreateDataBean, PushBody pushBody, Long optUserId) {
        this.customerCreateDataBean = customerCreateDataBean;
        this.pushBody = pushBody;
        this.optUserId = optUserId;
    }

    public CustomerCreateDataBean getCustomerCreateDataBean() {
        return customerCreateDataBean;
    }

    public void setCustomerCreateDataBean(CustomerCreateDataBean customerCreateDataBean) {
        this.customerCreateDataBean = customerCreateDataBean;
    }

    public PushBody getPushBody() {
        return pushBody;
    }

    public void setPushBody(PushBody pushBody) {
        this.pushBody = pushBody;
    }

    public Long getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(Long optUserId) {
        this.optUserId = optUserId;
    }
}
