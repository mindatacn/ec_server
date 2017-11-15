package com.mindata.ecserver.ec.model.response;

import com.mindata.ecserver.ec.model.base.BaseEcData;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
public class CustomerTagData extends BaseEcData{
    private CustomerGroupBean customerGroupBean;

    private List<CustomerTagDataBean> classBeanList;

    public CustomerGroupBean getCustomerGroupBean() {
        return customerGroupBean;
    }

    public void setCustomerGroupBean(CustomerGroupBean customerGroupBean) {
        this.customerGroupBean = customerGroupBean;
    }

    public List<CustomerTagDataBean> getClassBeanList() {
        return classBeanList;
    }

    public void setClassBeanList(List<CustomerTagDataBean> classBeanList) {
        this.classBeanList = classBeanList;
    }
}
