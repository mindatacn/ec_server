package com.mindata.ecserver.ec.model.response;

import com.mindata.ecserver.ec.model.base.BaseEcData;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
public class CustomerGroupData extends BaseEcData{
    private List<CustomerGroupBean> data;

    public List<CustomerGroupBean> getData() {
        return data;
    }

    public void setData(List<CustomerGroupBean> data) {
        this.data = data;
    }
}
