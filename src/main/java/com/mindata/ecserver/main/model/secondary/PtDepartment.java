package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/10/26.
 * 部门
 */
@Entity
@Table(name = "pt_department", indexes = {@Index(name = "company_id", columnList =
        "companyId")})
public class PtDepartment extends BaseEntity {

    /**
     * 公司ID
     */
    private Long companyId;
    /**
     * 名称
     */
    private String name;
    /**
     * 父部门id
     */
    private Long parentId;
    /**
     * 部门leader（id1，id2）
     */
    private String leaders;
    /**
     * 在EC的父部门id
     */
    private Long ecParentDeptId;
    /**
     * 在EC的部门id
     */
    private Long ecDeptId;
    /**
     * 在EC的sort
     */
    private Integer sort;
    /**
     * 默认推送数量阈值
     */
    private Integer threshold;
    /**
     * 备注
     */
    private String memo;
    /**
     * 状态，（0正常，-1被删除）
     */
    private Integer state;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getEcDeptId() {
        return ecDeptId;
    }

    public void setEcDeptId(Long ecDeptId) {
        this.ecDeptId = ecDeptId;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getEcParentDeptId() {
        return ecParentDeptId;
    }

    public void setEcParentDeptId(Long ecParentDeptId) {
        this.ecParentDeptId = ecParentDeptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "PtDepartment{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", ecParentDeptId=" + ecParentDeptId +
                ", ecDeptId=" + ecDeptId +
                ", sort=" + sort +
                ", threshold=" + threshold +
                ", memo='" + memo + '\'' +
                '}';
    }
}
