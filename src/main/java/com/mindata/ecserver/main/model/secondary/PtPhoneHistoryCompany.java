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
@Table(name = "pt_phone_history_company", indexes = {@Index(name = "company_id", columnList =
        "companyId"), @Index(name = "start_time", columnList =
        "startTime")})
public class PtPhoneHistoryCompany extends BaseHistoryEntity {
    /**
     * 公司id
     */
    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
