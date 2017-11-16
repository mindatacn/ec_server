package com.mindata.ecserver.ec.model.response;

import com.mindata.ecserver.ec.model.base.BaseEcData;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
public class CustomerTagData extends BaseEcData{
    private List<CustomerTagDataBean> data;

    public List<CustomerTagDataBean> getData() {
        return data;
    }

    public void setData(List<CustomerTagDataBean> data) {
        this.data = data;
    }
}
