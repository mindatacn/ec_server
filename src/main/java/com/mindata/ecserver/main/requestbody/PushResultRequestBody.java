package com.mindata.ecserver.main.requestbody;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 * 线索历史功能查询条件
 */
public class PushResultRequestBody {
    /**
     * 公司名
     */
    private String companyName;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 沟通结果
     */
    private Integer saleState;
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
     * 来源
     */
    private List<Integer> websiteIds;
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

    public Integer getSaleState() {
        return saleState;
    }

    public void setSaleState(Integer saleState) {
        this.saleState = saleState;
    }

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

    public List<Integer> getWebsiteIds() {
        return websiteIds;
    }

    public void setWebsiteIds(List<Integer> websiteIds) {
        this.websiteIds = websiteIds;
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PushResultRequestBody{" +
                "companyName='" + companyName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", saleState=" + saleState +
                ", provinces=" + provinces +
                ", cities=" + cities +
                ", vocations=" + vocations +
                ", websiteIds=" + websiteIds +
                ", size=" + size +
                ", page=" + page +
                ", orderBy='" + orderBy + '\'' +
                ", order=" + order +
                '}';
    }
}

