package com.mindata.ecserver.main.requestbody;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public class ContactRequestBody {
    /**
     * 省、直辖市集合
     */
    private List<String> provinces;
    /**
     * 市、区集合
     */
    private List<String> cities;
    /**
     * 行业集合
     */
    private List<Integer> vocations;
    /**
     * 人员数量
     */
    private List<Integer> memberSizeTags;
    /**
     * 来源
     */
    private List<Integer> websiteIds;
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
    /*ES结束*/

    /**
     * 每页多少个
     */
    private Integer size;
    /**
     * 当前第几页
     */
    private Integer page;
    /**
     * 按什么排序
     */
    private String orderBy;
    /**
     * 排序方向（默认DESC，true为ASC）
     */
    private Boolean order;

    public Boolean getOrder() {
        return order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<String> provinces) {
        this.provinces = provinces;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<Integer> getVocations() {
        return vocations;
    }

    public void setVocations(List<Integer> vocations) {
        this.vocations = vocations;
    }

    public List<Integer> getMemberSizeTags() {
        return memberSizeTags;
    }

    public void setMemberSizeTags(List<Integer> memberSizeTags) {
        this.memberSizeTags = memberSizeTags;
    }

    public List<Integer> getWebsiteIds() {
        return websiteIds;
    }

    public void setWebsiteIds(List<Integer> websiteIds) {
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

    @Override
    public String toString() {
        return "ContactRequestBody{" +
                "provinces=" + provinces +
                ", cities=" + cities +
                ", vocations=" + vocations +
                ", memberSizeTags=" + memberSizeTags +
                ", websiteIds=" + websiteIds +
                ", hasMobile=" + hasMobile +
                ", needSale=" + needSale +
                '}';
    }
}

