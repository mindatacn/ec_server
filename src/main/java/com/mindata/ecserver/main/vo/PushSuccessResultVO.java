package com.mindata.ecserver.main.vo;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
public class PushSuccessResultVO {
    private Long id;
    /**
     * 线索id
     */
    private Long contactId;
    /**
     * 公司名
     */
    private String company;
    /**
     * 跟进人
     */
    private String followUser;
    /**
     * 跟进团队
     */
    private String followDept;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getFollowUser() {
        return followUser;
    }

    public void setFollowUser(String followUser) {
        this.followUser = followUser;
    }

    public String getFollowDept() {
        return followDept;
    }

    public void setFollowDept(String followDept) {
        this.followDept = followDept;
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

    @Override
    public String toString() {
        return "PushSuccessResultVO{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", followUser='" + followUser + '\'' +
                ", followDept='" + followDept + '\'' +
                ", createTime=" + createTime +
                ", lastContactTime=" + lastContactTime +
                ", contactDuration=" + contactDuration +
                ", saleState=" + saleState +
                '}';
    }
}
