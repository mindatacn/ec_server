package com.mindata.ecserver.main.vo;

import com.mindata.ecserver.util.CommonUtil;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class PhoneHistoryBeanVO {
    /**
     * 总拨打数量
     */
    private Long totalCallCount;
    /**
     * 总拨打时长
     */
    private Long totalCallTime;
    /**
     * 总联系人数量
     */
    private Long totalCustomer;
    /**
     * 推送的总拨打数量
     */
    private Long pushCount;
    /**
     * 非推送的总拨打数量
     */
    private Long noPushCount;
    /**
     * 通话时长大于0的总数量
     */
    private Long validCount;
    /**
     * 推送的拨打总时长
     */
    private Long pushCallTime;
    /**
     * 推送的总联系人数量
     */
    private Long pushCustomer;
    /**
     * 推送的通话时长大于0的总数量
     */
    private Long pushValidCount;

    public PhoneHistoryBeanVO() {
    }

    public PhoneHistoryBeanVO(Object[] objects) {
        //sum(totalCallTime), sum(totalCallCount), sum(totalCustomer), sum(pushCount), sum(validCount) " +
        totalCallTime = CommonUtil.parseObject(objects[0]);
        totalCallCount = CommonUtil.parseObject(objects[1]);
        totalCustomer = CommonUtil.parseObject(objects[2]);
        pushCount = CommonUtil.parseObject(objects[3]);
        validCount = CommonUtil.parseObject(objects[4]);
        noPushCount = CommonUtil.parseObject(objects[5]);
        pushCallTime = CommonUtil.parseObject(objects[6]);
        pushCustomer = CommonUtil.parseObject(objects[7]);
        pushValidCount = CommonUtil.parseObject(objects[8]);
    }

    public Long getPushCallTime() {
        return pushCallTime;
    }

    public void setPushCallTime(Long pushCallTime) {
        this.pushCallTime = pushCallTime;
    }

    public Long getPushCustomer() {
        return pushCustomer;
    }

    public void setPushCustomer(Long pushCustomer) {
        this.pushCustomer = pushCustomer;
    }

    public Long getPushValidCount() {
        return pushValidCount;
    }

    public void setPushValidCount(Long pushValidCount) {
        this.pushValidCount = pushValidCount;
    }

    public Long getTotalCallCount() {
        return totalCallCount;
    }

    public void setTotalCallCount(Long totalCallCount) {
        this.totalCallCount = totalCallCount;
    }

    public Long getTotalCallTime() {
        return totalCallTime;
    }

    public void setTotalCallTime(Long totalCallTime) {
        this.totalCallTime = totalCallTime;
    }

    public Long getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(Long totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public Long getPushCount() {
        return pushCount;
    }

    public void setPushCount(Long pushCount) {
        this.pushCount = pushCount;
    }

    public Long getNoPushCount() {
        return noPushCount;
    }

    public void setNoPushCount(Long noPushCount) {
        this.noPushCount = noPushCount;
    }

    public Long getValidCount() {
        return validCount;
    }

    public void setValidCount(Long validCount) {
        this.validCount = validCount;
    }

    @Override
    public String toString() {
        return "PhoneHistoryBeanVO{" +
                "totalCallCount=" + totalCallCount +
                ", totalCallTime=" + totalCallTime +
                ", totalCustomer=" + totalCustomer +
                ", pushCount=" + pushCount +
                ", validCount=" + validCount +
                ", noPushCount=" + noPushCount +
                '}';
    }
}
