package com.mindata.ecserver.main.model.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@MappedSuperclass
public class BaseHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 打电话日期（精确到日）
     */
    private Date startTime;
    /**
     * 总电话时长
     */
    private Long totalCallTime;
    /**
     * 是我们推送的总电话时长
     */
    private Long pushCallTime;
    /**
     * 总的打电话数量
     */
    private Long totalCallCount;
    /**
     * 使用我们的号码的总打电话数量
     */
    private Long pushCount;
    /**
     * 总联系人数量（对客户id排重）
     */
    private Long totalCustomer;
    /**
     * 总联系人中有哪些是我们推送的客户，排重
     */
    private Long pushCustomer;
    /**
     * 总的沟通时间大于0的数量
     */
    private Long validCount;
    /**
     * 是我们推送的接通的数量
     */
    private Long pushValidCount;
    /**
     * 其他渠道数量
     */
    private Long noPushCount;

    private Date createTime;

    private Date updateTime;

    public Long getPushCallTime() {
        return pushCallTime;
    }

    public void setPushCallTime(Long pushCallTime) {
        this.pushCallTime = pushCallTime;
    }

    public Long getPushCount() {
        return pushCount;
    }

    public void setPushCount(Long pushCount) {
        this.pushCount = pushCount;
    }

    public Long getPushValidCount() {
        return pushValidCount;
    }

    public void setPushValidCount(Long pushValidCount) {
        this.pushValidCount = pushValidCount;
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

    public Long getPushCustomer() {
        return pushCustomer;
    }

    public void setPushCustomer(Long pushCustomer) {
        this.pushCustomer = pushCustomer;
    }

    public Long getValidCount() {
        return validCount;
    }

    public void setValidCount(Long validCount) {
        this.validCount = validCount;
    }

    public Long getNoPushCount() {
        return noPushCount;
    }

    public void setNoPushCount(Long noPushCount) {
        this.noPushCount = noPushCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
