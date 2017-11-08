package com.mindata.ecserver.ec.model.response;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 一条电话联系记录
 */
public class PhoneHistoryDataBean {
    /**
     * EC员工id
     */
    private Long userId;
    /**
     * 开始打电话的时间
     */
    private String starttime;
    /**
     * 电话号码，被拨打的电话号码
     */
    private String calltono;
    /**
     * 客户id
     */
    private Long crmId;
    /**
     * 通话时长
     */
    private String calltime;
    /**
     * 通话方式：
     * 1 电话
     * 2 传真
     * 3 电话会议
     * 4 盒子呼入
     */
    private Integer type;
    /**
     * 录音地址
     */
    private String path;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户公司
     */
    private String customerCompany;
    /**
     * md5
     */
    private String md5;

    @Override
    public String toString() {
        return "PhoneHistoryDataBean{" +
                "starttime=" + starttime +
                ", calltono='" + calltono + '\'' +
                ", crmId=" + crmId +
                ", calltime='" + calltime + '\'' +
                ", type=" + type +
                ", userId=" + userId +
                ", path='" + path + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerCompany='" + customerCompany + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getCalltono() {
        return calltono;
    }

    public void setCalltono(String calltono) {
        this.calltono = calltono;
    }

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }

    public String getCalltime() {
        return calltime;
    }

    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
