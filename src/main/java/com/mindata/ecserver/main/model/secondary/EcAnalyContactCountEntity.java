package com.mindata.ecserver.main.model.secondary;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@Entity
@Table(name = "ec_analy_contact_count",
        indexes = {@Index(name = "analy_date", columnList =
                "analy_date")})
public class EcAnalyContactCountEntity {
    private int id;
    /**
     * 统计的是哪天
     */
    private Date analyDate;
    /**
     * 爬取的信息数量
     */
    private Integer compCount;
    /**
     * 已推送的数量
     */
    private Integer pushCount;
    /**
     * 推送成功的数量
     */
    private Integer pushSuccessCount;
    /**
     * 已初步沟通的数量
     */
    private Integer contactCount;
    /**
     * 有意向的数量
     */
    private Integer intentCount;
    private Date createTime;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "analy_date")
    public Date getAnalyDate() {
        return analyDate;
    }

    public void setAnalyDate(Date analyDate) {
        this.analyDate = analyDate;
    }

    @Basic
    @Column(name = "comp_count")
    public Integer getCompCount() {
        return compCount;
    }

    public void setCompCount(Integer compCount) {
        this.compCount = compCount;
    }

    @Basic
    @Column(name = "push_count")
    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

    @Basic
    @Column(name = "push_success_count")
    public Integer getPushSuccessCount() {
        return pushSuccessCount;
    }

    public void setPushSuccessCount(Integer pushSuccessCount) {
        this.pushSuccessCount = pushSuccessCount;
    }

    @Basic
    @Column(name = "contact_count")
    public Integer getContactCount() {
        return contactCount;
    }

    public void setContactCount(Integer contactCount) {
        this.contactCount = contactCount;
    }

    @Basic
    @Column(name = "intent_count")
    public Integer getIntentCount() {
        return intentCount;
    }

    public void setIntentCount(Integer intentCount) {
        this.intentCount = intentCount;
    }

    @Basic
    @Column(name = "Integerent_count")


    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (analyDate != null ? analyDate.hashCode() : 0);
        result = 31 * result + compCount;
        result = 31 * result + pushCount;
        result = 31 * result + pushSuccessCount;
        result = 31 * result + contactCount;
        result = 31 * result + intentCount;
        return result;
    }
}
