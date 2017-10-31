package com.mindata.ecserver.main.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/24.
 */
@Entity
@Table(name = "ec_contact_push_result",
        indexes = {@Index(name = "push_time", columnList = "push_time"),
                @Index(name = "contact_id", columnList = "contact_id")})
public class EcContactPushResultEntity {
    private int id;
    /**
     * 数据的id
     */
    private Integer contactId;
    /**
     * 推送成功失败
     */
    private Integer status;
    /**
     * 客户id
     */
    private Integer crmId;
    /**
     * 公司id
     */
    private Integer corpId;
    /**
     * 操作人id
     */
    private Integer oprUserId;
    /**
     * 跟进人id
     */
    private Integer followUserId;
    /**
     * 成功、失败的原因
     */
    private String message;
    /**
     * 推送时间
     */
    private Date pushTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "crm_id")
    public Integer getCrmId() {
        return crmId;
    }

    public void setCrmId(Integer crmId) {
        this.crmId = crmId;
    }

    @Basic
    @Column(name = "corp_id")
    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    @Basic
    @Column(name = "opr_user_id")
    public Integer getOprUserId() {
        return oprUserId;
    }

    public void setOprUserId(Integer oprUserId) {
        this.oprUserId = oprUserId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    @Basic
    @Column(name = "follow_user_id")
    public Integer getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Integer followUserId) {
        this.followUserId = followUserId;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "push_time")
    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    @Column(name = "contact_id")
    public Integer getContactId() {
        return contactId;
    }

    public void setContactNoPushId(Integer contactId) {
        this.contactId = contactId;
    }

}
