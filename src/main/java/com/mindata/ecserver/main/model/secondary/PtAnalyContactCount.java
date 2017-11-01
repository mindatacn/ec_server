package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@Entity
@Table(name = "pt_analy_contact_count",
        indexes = {@Index(name = "analy_date", columnList =
                "analyDate")})
public class PtAnalyContactCount extends BaseEntity {
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

    public Date getAnalyDate() {
        return analyDate;
    }

    public void setAnalyDate(Date analyDate) {
        this.analyDate = analyDate;
    }

    public Integer getCompCount() {
        return compCount;
    }

    public void setCompCount(Integer compCount) {
        this.compCount = compCount;
    }

    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

    public Integer getPushSuccessCount() {
        return pushSuccessCount;
    }

    public void setPushSuccessCount(Integer pushSuccessCount) {
        this.pushSuccessCount = pushSuccessCount;
    }

    public Integer getContactCount() {
        return contactCount;
    }

    public void setContactCount(Integer contactCount) {
        this.contactCount = contactCount;
    }

    public Integer getIntentCount() {
        return intentCount;
    }

    public void setIntentCount(Integer intentCount) {
        this.intentCount = intentCount;
    }
}
