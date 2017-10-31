package com.mindata.ecserver.main.requestbody;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public class CompanyBody {
    /**
     * 公司名称
     */
    private String name;
    /**
     * 公司前缀（将来配置员工账号均使用该前缀）
     */
    private String prefix;
    /**
     * 默认推送数量阈值
     */
    private Integer threshold;
    /**
     * 省code
     */
    private String province;
    /**
     * 城市code
     */
    private String city;
    /**
     * 联系人
     */
    private String contactPerson;
    /**
     * 行业标签
     */
    private Integer vocationTag;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 备注
     */
    private String memo;
    /**
     * appId
     */
    private String appId;
    /**
     * APPSecret
     */
    private String appSecret;

    private String account;

    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Integer getVocationTag() {
        return vocationTag;
    }

    public void setVocationTag(Integer vocationTag) {
        this.vocationTag = vocationTag;
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
}
