package com.mindata.ecserver.main.vo;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
public class PushSuccessResultVO {
    private Integer id;
    /**
     * 公司名
     */
    private String company;
    /**
     * 推送时间
     */
    private Date createTime;
    /**
     * 最后沟通时间
     */
    private Date lastContactTime;
    /**
     * 沟通时长
     */
    private Integer contactDuration;
    /**
     * 结果
     */
    private Integer saleState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(Date lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

    public Integer getContactDuration() {
        return contactDuration;
    }

    public void setContactDuration(Integer contactDuration) {
        this.contactDuration = contactDuration;
    }

    public Integer getSaleState() {
        return saleState;
    }

    public void setSaleState(Integer saleState) {
        this.saleState = saleState;
    }
}
