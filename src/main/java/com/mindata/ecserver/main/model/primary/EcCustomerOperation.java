package com.mindata.ecserver.main.model.primary;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/27.
 */
@Entity
@Table(name = "ec_customer_operation")
public class EcCustomerOperation {
    private Long id;
    /**
     * 客户id
     */
    private Long crmId;
    /**
     * 操作（更新标签、转让客户）
     */
    private String operateType;
    /**
     * 操作类型
     */
    private Integer statusCode;
    /**
     * 操作时间
     */
    private Date operateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
