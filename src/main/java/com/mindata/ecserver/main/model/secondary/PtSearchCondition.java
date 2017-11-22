package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 检索条件表
 */
@Entity
@Table(name = "pt_search_condition", indexes = {@Index(name = "user_id", columnList =
        "userId")})
public class PtSearchCondition extends BaseEntity {
    /**
     * 检索人
     */
    private Long userId;
    /**
     * 省、直辖市集合
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private Set<String> provinces;
    /**
     * 市、区集合
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private Set<String> cities;
    /**
     * 行业集合
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private Set<Integer> vocations;
    /**
     * 人员数量
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private Set<Integer> memberSizeTags;
    /**
     * 来源
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private Set<Integer> websiteIds;

    /**
     * 是否有手机号
     */
    private Boolean hasMobile;
    /**
     * 是否招聘销售
     */
    private Boolean needSale;

    /*ES里存的字段*/
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 招聘岗位
     */
    private String jobName;
    /**
     * 公司简介
     */
    private String comintro;
    /**
     * 全文检索
     */
    private String extra;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(Set<String> provinces) {
        this.provinces = provinces;
    }

    public Set<String> getCities() {
        return cities;
    }

    public void setCities(Set<String> cities) {
        this.cities = cities;
    }

    public Set<Integer> getVocations() {
        return vocations;
    }

    public void setVocations(Set<Integer> vocations) {
        this.vocations = vocations;
    }

    public Set<Integer> getMemberSizeTags() {
        return memberSizeTags;
    }

    public void setMemberSizeTags(Set<Integer> memberSizeTags) {
        this.memberSizeTags = memberSizeTags;
    }

    public Set<Integer> getWebsiteIds() {
        return websiteIds;
    }

    public void setWebsiteIds(Set<Integer> websiteIds) {
        this.websiteIds = websiteIds;
    }

    public Boolean getHasMobile() {
        return hasMobile;
    }

    public void setHasMobile(Boolean hasMobile) {
        this.hasMobile = hasMobile;
    }

    public Boolean getNeedSale() {
        return needSale;
    }

    public void setNeedSale(Boolean needSale) {
        this.needSale = needSale;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getComintro() {
        return comintro;
    }

    public void setComintro(String comintro) {
        this.comintro = comintro;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
