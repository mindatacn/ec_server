package com.mindata.ecserver.main.requestbody;

/**
 * @author wuweifeng wrote on 2018/1/24.
 */
public class OrderBody {
    private Long id;
    private Long companyId;
    private Integer money;
    private Long productId;
    private String effectiveDate;
    private String expiryDate;
    private String memo;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExpiryDate(String expiryDate) {
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
        return "OrderBody{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", money=" + money +
                ", productId=" + productId +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
