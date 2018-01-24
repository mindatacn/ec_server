package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author hanliqiang wrote on 2018/1/22
 */
@Entity
@Table(name = "pt_order")
public class PtOrder extends BaseEntity {
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 购买金额（元）
     */
    private Integer money;
    /**
     * 生效日期
     */
    private Date effectiveDate;
    /**
     * 失效日期
     */
    private Date expiryDate;
    /**
     * 备注
     */
    private String memo;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "PtOrder{" +
                "productId=" + productId +
                ", companyId=" + companyId +
                ", money=" + money +
                ", effectiveDate=" + effectiveDate +
                ", expiryDate=" + expiryDate +
                ", memo='" + memo + '\'' +
                '}';
    }
}
