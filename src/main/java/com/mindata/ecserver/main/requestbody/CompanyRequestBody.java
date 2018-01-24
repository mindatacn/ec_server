package com.mindata.ecserver.main.requestbody;

/**
 * @author hanliqiang wrote on 2018/1/22
 */
public class CompanyRequestBody {
    /**
     * 客户名称
     */
    private String companyName;
    /**
     * 客户状态
     */
    private Integer buyStatus;
    /**
     * 产品名称
     */
    private Long productId;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getOrder() {
        return order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }


    @Override
    public String toString() {
        return "CompanyRequestBody{" +
                "companyName='" + companyName + '\'' +
                ", buyStatus=" + buyStatus +
                ", productId=" + productId +
                ", size=" + size +
                ", page=" + page +
                ", orderBy='" + orderBy + '\'' +
                ", order=" + order +
                '}';
    }
}