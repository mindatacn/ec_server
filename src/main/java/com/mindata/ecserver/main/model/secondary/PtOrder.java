package com.mindata.ecserver.main.model.secondary;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hanliqiang wrote on 2018/1/22
 */
@Entity
@Table(name = "pt_order")
public class PtOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 购买金额
     */
    private BigDecimal money;
    /**
     * 生效日期
     */
    private Date effectiveDate;
    /**
     * 失效日期
     */
    private Date expiryDate;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
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
}
