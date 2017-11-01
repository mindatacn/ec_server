package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/1.
 * 客户跟踪结果
 */
@Entity
@Table(name = "pt_sale_result",
        indexes = {@Index(name = "create_time", columnList = "createTime"),
                @Index(name = "crm_id", columnList = "crmId"),
                @Index(name = "follow_user_id", columnList = "followUserId")})
public class PtSaleResult extends BaseEntity {

    /**
     * 在EC的客户id
     */
    private Integer crmId;
    /**
     * 销售状态（跟进中、已成交等等）
     */
    private Integer saleState;
    /**
     * 跟进人id
     */
    private Integer followUserId;
    /**
     * 最后沟通时间
     */
    private Date lastContactTime;

    public Integer getCrmId() {
        return crmId;
    }

    public void setCrmId(Integer crmId) {
        this.crmId = crmId;
    }

    public Integer getSaleState() {
        return saleState;
    }

    public Integer getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Integer followUserId) {
        this.followUserId = followUserId;
    }

    public void setSaleState(Integer saleState) {
        this.saleState = saleState;
    }

    public Date getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(Date lastContactTime) {
        this.lastContactTime = lastContactTime;
    }
}
