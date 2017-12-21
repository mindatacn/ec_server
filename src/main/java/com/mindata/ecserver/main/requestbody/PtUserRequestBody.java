package com.mindata.ecserver.main.requestbody;

/**
 * @author hanliqiang wrote on 2017/12/21
 */
public class PtUserRequestBody {
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
}
