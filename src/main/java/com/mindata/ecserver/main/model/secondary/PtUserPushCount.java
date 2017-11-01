package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/26.
 * 用户每天推送数量统计
 */
@Entity
@Table(name = "pt_user_push_count", indexes = {@Index(name = "user_id", columnList =
        "userId")})
public class PtUserPushCount extends BaseEntity {
    /**
     * userID
     */
    private Integer userId;
    /**
     * 已经推送了的数量
     */
    private Integer pushedCount;
    /**
     * 被设置的推送阈值，如果没有设置则用部门的默认推送数量阈值
     */
    private Integer threshold;
    /**
     * 哪一天操作
     */
    private Date pushDate;
    /**
     * 部门id
     */
    private Integer departmentId;
    /**
     * 公司id
     */
    private Integer companyId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getPushedCount() {
        return pushedCount;
    }

    public void setPushedCount(Integer pushedCount) {
        this.pushedCount = pushedCount;
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

}
