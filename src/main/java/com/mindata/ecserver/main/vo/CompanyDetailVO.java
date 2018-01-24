package com.mindata.ecserver.main.vo;

import java.util.Date;

/**
 * @author hanliqiang wrote on 2018/1/23
 */
public class CompanyDetailVO {
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 联系人
     */
    private String contactPerson;
    /**
     * 账号
     */
    private String account;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 状态
     */
    private Integer buyStatus;

    private String address;
    /**
     * 需确认
     */
    private Integer vocationTag;

    private String mobile;

    private String phone;

    private String email;

    private String memo;

    private Date createTime;

    /**
     * appId
     */
    private String appId;
    /**
     * APPSecret
     */
    private String appSecret;

    /**
     * 生效日期
     */
    private Date effectiveDate;
    /**
     * 失效日期
     */
    private Date expiryDate;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVocationTag() {
        return vocationTag;
    }

    public void setVocationTag(Integer vocationTag) {
        this.vocationTag = vocationTag;
    }

    @Override
    public String toString() {
        return "CompanyDetailVO{" +
                "productName='" + productName + '\'' +
                ", name='" + name + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", account='" + account + '\'' +
                ", roleName='" + roleName + '\'' +
                ", buyStatus=" + buyStatus +
                ", address='" + address + '\'' +
                ", vocationTag=" + vocationTag +
                ", mobile='" + mobile + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", memo='" + memo + '\'' +
                ", createTime=" + createTime +
                ", appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
