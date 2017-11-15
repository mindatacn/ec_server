package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseHistoryEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 个人单日通话历史统计表
 */
@Entity
@Table(name = "pt_phone_history_user", indexes = {@Index(name = "user_id", columnList =
        "userId"), @Index(name = "start_time", columnList =
        "startTime")})
public class PtPhoneHistoryUser extends BaseHistoryEntity {
    /**
     * 员工id
     */
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
