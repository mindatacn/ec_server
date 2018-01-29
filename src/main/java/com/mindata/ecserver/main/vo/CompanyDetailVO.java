package com.mindata.ecserver.main.vo;

import java.util.Date;

/**
 * @author hanliqiang wrote on 2018/1/23
 */
public class CompanyDetailVO {
    /**
     * appId
     */
    private String appId;
    /**
     * APPSecret
     */
    private String appSecret;
    private String city;
    /**
     * 联系人
     */
    private String contactPerson;
    private String mobile;
    /**
     * 公司名称
     */
    private String name;
    private String phone;
    private String prefix;
    private String province;
    /**
     * 需确认
     */
    private Integer vocationTag;
    private Long corpId;
    /**
     * 默认推送数量阈值
     */
    private Integer threshold;
    private String address;
    /**
     * 生效日期
     */
    private Date effectiveDate;
    /**
     * 失效日期
     */
    private Date expiryDate;
    /**
     * 账号
     */
    private String account;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 状态
     */
    private Integer buyStatus;
    private Integer status;

    private String email;

    private String memo;

    private Date createTime;


    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getCorpId() {
        return corpId;
    }

    public void setCorpId(Long corpId) {
        this.corpId = corpId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "CompanyDetailVO{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", city='" + city + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", prefix='" + prefix + '\'' +
                ", province='" + province + '\'' +
                ", vocationTag=" + vocationTag +
                ", corpId=" + corpId +
                ", threshold=" + threshold +
                ", address='" + address + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", expiryDate=" + expiryDate +
                ", account='" + account + '\'' +
                ", productId=" + productId +
                ", roleId=" + roleId +
                ", buyStatus=" + buyStatus +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", memo='" + memo + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
