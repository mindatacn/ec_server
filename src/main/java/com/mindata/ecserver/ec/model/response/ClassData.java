package com.mindata.ecserver.ec.model.response;

import com.mindata.ecserver.ec.model.base.BaseEcData;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
public class ClassData extends BaseEcData{
    private CustomerGroupBean customerGroupBean;

    private List<ClassDataBean> classBeanList;

    public CustomerGroupBean getCustomerGroupBean() {
        return customerGroupBean;
    }

    public void setCustomerGroupBean(CustomerGroupBean customerGroupBean) {
        this.customerGroupBean = customerGroupBean;
    }

    public List<ClassDataBean> getClassBeanList() {
        return classBeanList;
    }

    public void setClassBeanList(List<ClassDataBean> classBeanList) {
        this.classBeanList = classBeanList;
    }
}
