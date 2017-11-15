package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 个人单日通话历史统计表
 */
@Entity
@Table(name = "pt_phone_history_dept", indexes = {@Index(name = "dept_id", columnList =
        "deptId"), @Index(name = "start_time", columnList =
        "startTime")})
public class PtPhoneHistoryDept extends BaseEntity {
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 统计日期（精确到日）
     */
    private Date startTime;
    /**
     * 总电话时长
     */
    private Long totalCallTime;
    /**
     * 总打电话数量
     */
    private Long totalCallCount;
    /**
     * 总联系人数量（对客户id排重）
     */
    private Long totalCustomer;
    /**
     * 看该天总联系人数量中，有哪些是我们推送的
     */
    private Long pushCount;
    /**
     * 沟通时间大于0的数量
     */
    private Long validCount;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getValidCount() {
        return validCount;
    }

    public void setValidCount(Long validCount) {
        this.validCount = validCount;
    }

    public Long getPushCount() {
        return pushCount;
    }

    public void setPushCount(Long pushCount) {
        this.pushCount = pushCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getTotalCallTime() {
        return totalCallTime;
    }

    public void setTotalCallTime(Long totalCallTime) {
        this.totalCallTime = totalCallTime;
    }

    public Long getTotalCallCount() {
        return totalCallCount;
    }

    public void setTotalCallCount(Long totalCallCount) {
        this.totalCallCount = totalCallCount;
    }

    public Long getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(Long totalCustomer) {
        this.totalCustomer = totalCustomer;
    }
}
