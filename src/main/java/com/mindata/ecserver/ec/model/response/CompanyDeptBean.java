package com.mindata.ecserver.ec.model.response;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
public class CompanyDeptBean {
    private Integer deptId;
    private Integer parentDeptId;
    private String deptName;
    private Integer sort;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(Integer parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "CompanyDeptBean{" +
                "deptId=" + deptId +
                ", parentDeptId=" + parentDeptId +
                ", deptName='" + deptName + '\'' +
                ", sort=" + sort +
                '}';
    }
}
