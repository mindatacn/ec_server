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
@Table(name = "pt_phone_history_dept", indexes = {@Index(name = "ec_user_id", columnList =
        "ecUserId"), @Index(name = "start_time", columnList =
        "startTime")})
public class PtPhoneHistoryDept extends BaseEntity {
    /**
     * 部门id
     */
    private Integer deptId;
    /**
     * 统计日期（精确到日）
     */
    private Date startTime;
    /**
     * 总电话时长
     */
    private Integer totalCallTime;
    /**
     * 总打电话数量
     */
    private Integer totalCallCount;
    /**
     * 总联系人数量（对客户id排重）
     */
    private Integer totalCustomer;
    /**
     * 看该天总联系人数量中，有哪些是我们推送的
     */
    private Integer pushCount;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getTotalCallTime() {
        return totalCallTime;
    }

    public void setTotalCallTime(Integer totalCallTime) {
        this.totalCallTime = totalCallTime;
    }

    public Integer getTotalCallCount() {
        return totalCallCount;
    }

    public void setTotalCallCount(Integer totalCallCount) {
        this.totalCallCount = totalCallCount;
    }

    public Integer getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(Integer totalCustomer) {
        this.totalCustomer = totalCustomer;
    }
}
