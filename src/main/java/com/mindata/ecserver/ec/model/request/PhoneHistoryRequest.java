package com.mindata.ecserver.ec.model.request;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 获取电话记录
 */
public class PhoneHistoryRequest {
    /**
     * 可选, 部门ID，希望导出的操作员工所在部门ID，可输入多个值，用分号(;)隔开。
     */
    private String deptIds;
    /**
     * 可选, 员工ID，希望导出的操作员工ID，可输入多个值，用分号(;)隔开。
     */
    private String userIds;
    /**
     * 可选, 客户ID，被呼叫的客户的ID，可输入多个值,用分号(;)隔开。
     */
    private String crmIds;
    /**
     * 必填，统计开始时间，精确到年月日，如：2016-03-10
     */
    private String startDate;
    /**
     * 必填，统计结束时间，精确到年月日，如：2016-03-10
     */
    private String endDate;
    /**
     * 被呼叫的电话号码，只能查单个电话。
     */
    private String phoneNo;
    /**
     * pageNo 可选,分页请求时当前请求第几页的数据，从1开始。
     */
    private int pageNo;

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getCrmIds() {
        return crmIds;
    }

    public void setCrmIds(String crmIds) {
        this.crmIds = crmIds;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
