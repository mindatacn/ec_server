package com.mindata.ecserver.main.vo;

import com.mindata.ecserver.util.CommonUtil;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class PhoneHistoryBeanVO {
    private Long totalCallCount;
    private Long totalCallTime;
    private Long totalCustomer;
    private Long pushCount;
    private Long validCount;
    private String date;

    public PhoneHistoryBeanVO() {
    }

    public PhoneHistoryBeanVO(Object[] objects) {
        //sum(totalCallTime), sum(totalCallCount), sum(totalCustomer), sum(pushCount), sum(validCount) " +
        totalCallTime = CommonUtil.parseObject(objects[0]);
        totalCallCount = CommonUtil.parseObject(objects[1]);
        totalCustomer = CommonUtil.parseObject(objects[2]);
        pushCount = CommonUtil.parseObject(objects[3]);
        validCount = CommonUtil.parseObject(objects[4]);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Long getValidCount() {
        return validCount;
    }

    public void setValidCount(Long validCount) {
        this.validCount = validCount;
    }
}
