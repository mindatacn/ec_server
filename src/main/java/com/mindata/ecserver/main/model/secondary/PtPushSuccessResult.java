package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.*;

/**
 * @author wuweifeng wrote on 2017/10/24.
 * 推送成功的表
 */
@Entity
@Table(name = "pt_push_success_result",
        indexes = {@Index(name = "create_time", columnList = "createTime"),
                @Index(name = "follow_user_id", columnList = "followUserId")})
public class PtPushSuccessResult extends BaseEntity {
    /**
     * 数据的id
     */
    private Integer contactId;
    /**
     * 线索中的公司名
     */
    private String companyName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 1级行业
     */
    private Integer vocation1;
    /**
     * 2级行业
     */
    private Integer vocation2;
    /**
     * 沟通结果
     */
    private Integer saleState;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 来源（58、桔子）
     */
    private Integer websiteId;
    /**
     * 在EC的客户id
     */
    private Integer crmId;
    /**
     * 跟进人的公司id
     */
    private Integer companyId;
    /**
     * 操作人id
     */
    private Integer optUserId;
    /**
     * 跟进人id
     */
    private Integer followUserId;
    /**
     * 跟进人的部门id
     */
    private Integer departmentId;

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getVocation1() {
        return vocation1;
    }

    public void setVocation1(Integer vocation1) {
        this.vocation1 = vocation1;
    }

    public Integer getVocation2() {
        return vocation2;
    }

    public void setVocation2(Integer vocation2) {
        this.vocation2 = vocation2;
    }

    public Integer getSaleState() {
        return saleState;
    }

    public void setSaleState(Integer saleState) {
        this.saleState = saleState;
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

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    public Integer getCrmId() {
        return crmId;
    }

    public void setCrmId(Integer crmId) {
        this.crmId = crmId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(Integer optUserId) {
        this.optUserId = optUserId;
    }

    public Integer getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Integer followUserId) {
        this.followUserId = followUserId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "PtPushSuccessResult{" +
                "contactId=" + contactId +
                ", companyName='" + companyName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", vocation1=" + vocation1 +
                ", vocation2=" + vocation2 +
                ", saleState=" + saleState +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", websiteId=" + websiteId +
                ", crmId=" + crmId +
                ", companyId=" + companyId +
                ", optUserId=" + optUserId +
                ", followUserId=" + followUserId +
                ", departmentId=" + departmentId +
                '}';
    }
}
