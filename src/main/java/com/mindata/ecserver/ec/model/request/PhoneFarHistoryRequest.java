package com.mindata.ecserver.ec.model.request;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 获取早期电话记录
 */
public class PhoneFarHistoryRequest {
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
     * 年
     */
    private int year;
    /**
     * 月
     */
    private int month;
    /**
     * 统计时间开始日，如：01 取值[01-31] 不填默认取01
     */
    private int startDayOfMonth;
    /**
     * 统计时间结束日，如：01 取值[01-31] 不填默认取至当月最后一天
     */
    private int endDayOfMonth;
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

    public int getStartDayOfMonth() {
        return startDayOfMonth;
    }

    public void setStartDayOfMonth(int startDayOfMonth) {
        this.startDayOfMonth = startDayOfMonth;
    }

    public int getEndDayOfMonth() {
        return endDayOfMonth;
    }

    public void setEndDayOfMonth(int endDayOfMonth) {
        this.endDayOfMonth = endDayOfMonth;
    }

    public String getCrmIds() {
        return crmIds;
    }

    public void setCrmIds(String crmIds) {
        this.crmIds = crmIds;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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
