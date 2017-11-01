package com.mindata.ecserver.ec.model.response;

import com.mindata.ecserver.ec.model.base.BaseEcData;

/**
 * @author wuweifeng wrote on 2017/10/23.
 * 批量创建客户
 */
public class CustomerCreateData extends BaseEcData {

    private CustomerCreateDataBean data;

    public CustomerCreateDataBean getData() {
        return data;
    }

    public void setData(CustomerCreateDataBean data) {
        this.data = data;
    }
}
