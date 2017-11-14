package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/11/1.
 * 推送失败的model
 */
@Entity
@Table(name = "pt_push_failure_result",
        indexes = {@Index(name = "create_time", columnList = "createTime"),
                @Index(name = "follow_user_id", columnList = "followUserId")})
public class PtPushFailureResult extends BaseEntity {
    /**
     * 线索公司的id
     */
    private Long contactId;
    /**
     * 操作人id
     */
    private Long optUserId;
    /**
     * 跟进人id
     */
    private Long followUserId;
    /**
     * 失败原因
     */
    private String failureCause;
    /**
     * 导致失败的字段集合
     */
    private String failureFields;
    /**
     * 当导入的客户已存在DB中时，该字段表示已存在的客户名，默认值为空
     */
    private String existedCustomerName;
    /**
     * 当导入客户发生撞单时，该字段表示撞单客户在DB 中记录的跟进人，默认值为空
     */
    private String existedFollowUserName;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(Long optUserId) {
        this.optUserId = optUserId;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
    }

    public String getFailureFields() {
        return failureFields;
    }

    public void setFailureFields(String failureFields) {
        this.failureFields = failureFields;
    }

    public String getExistedCustomerName() {
        return existedCustomerName;
    }

    public void setExistedCustomerName(String existedCustomerName) {
        this.existedCustomerName = existedCustomerName;
    }

    public String getExistedFollowUserName() {
        return existedFollowUserName;
    }

    public void setExistedFollowUserName(String existedFollowUserName) {
        this.existedFollowUserName = existedFollowUserName;
    }

    @Override
    public String toString() {
        return "PtPushFailureResult{" +
                "contactId=" + contactId +
                ", optUserId=" + optUserId +
                ", followUserId=" + followUserId +
                ", failureCause='" + failureCause + '\'' +
                ", failureFields='" + failureFields + '\'' +
                ", existedCustomerName='" + existedCustomerName + '\'' +
                ", existedFollowUserName='" + existedFollowUserName + '\'' +
                '}';
    }
}
