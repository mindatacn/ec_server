package com.mindata.ecserver.main.vo;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class PhoneHistoryDeptBeanVO extends PhoneHistoryBeanVO {
    private String deptName;

    public PhoneHistoryDeptBeanVO() {
    }

    public PhoneHistoryDeptBeanVO(Object[] objects) {
        super(objects);
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
