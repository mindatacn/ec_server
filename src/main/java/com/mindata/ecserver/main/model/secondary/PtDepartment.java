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
    private Integer companyId;
    /**
     * 名称
     */
    private String name;
    /**
     * 父部门id
     */
    private Integer parentId;
    /**
     * 在EC的父部门id
     */
    private Integer ecParentDeptId;
    /**
     * 在EC的部门id
     */
    private Integer ecDeptId;
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
     * 状态，（0正常，1被删除）
     */
    private Integer state;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public Integer getEcDeptId() {
        return ecDeptId;
    }

    public void setEcDeptId(Integer ecDeptId) {
        this.ecDeptId = ecDeptId;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEcParentDeptId() {
        return ecParentDeptId;
    }

    public void setEcParentDeptId(Integer ecParentDeptId) {
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
