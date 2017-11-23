package com.mindata.ecserver.main.vo;

import java.util.Date;

/**
 * @author hanliqiang wrote on 2017/11/16
 */
public class PushFailResultVO {
    /**
     * 编号Id
     */
    private Long id;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 推送时间
     */
    private Date pushTime;
    /**
     * 失败原因
     */
    private String failReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    @Override
    public String toString() {
        return "PushFailResultVO{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", pushTime=" + pushTime +
                ", failReason='" + failReason + '\'' +
                '}';
    }
}
