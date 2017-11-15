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
@Table(name = "pt_phone_history_dept", indexes = {@Index(name = "dept_id", columnList =
        "deptId"), @Index(name = "start_time", columnList =
        "startTime")})
public class PtPhoneHistoryDept extends BaseHistoryEntity {
    /**
     * 部门id
     */
    private Long deptId;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
