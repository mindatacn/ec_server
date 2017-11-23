package com.mindata.ecserver.main.requestbody;

/**
 * @author hanliqiang wrote on 2017/11/16
 */

public class PushFailRequestBody {
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 跟进团队
     */
    private Long deptId;
    /**
     * 推送人
     */
    private Long userId;

    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
