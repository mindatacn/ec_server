package com.mindata.ecserver.main.vo;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class PhoneHistoryDeptBeanVO extends PhoneHistoryBeanVO {
    private String deptName;
    private Long deptId;

    public PhoneHistoryDeptBeanVO() {
    }

    public PhoneHistoryDeptBeanVO(Object[] objects) {
        super(objects);
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "PhoneHistoryDeptBeanVO{" +
                "deptName='" + deptName + '\'' +
                ", deptId=" + deptId +
                '}';
    }
}
