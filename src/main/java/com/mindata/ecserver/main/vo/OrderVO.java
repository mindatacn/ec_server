package com.mindata.ecserver.main.vo;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2018/1/24.
 */
public class OrderVO {
    private Long id;
    /**
     * 产品name
     */
    private String productName;
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
     * 创建日期
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", companyId=" + companyId +
                ", money=" + money +
                ", effectiveDate=" + effectiveDate +
                ", expiryDate=" + expiryDate +
                ", createTime=" + createTime +
                ", memo='" + memo + '\'' +
                '}';
    }
}
