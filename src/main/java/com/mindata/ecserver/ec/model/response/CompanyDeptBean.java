package com.mindata.ecserver.ec.model.response;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
public class CompanyDeptBean {
    private Long deptId;
    private Long parentDeptId;
    private String deptName;
    private Integer sort;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(Long parentDeptId) {
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
