package com.mindata.ecserver.main.vo;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class PhoneHistoryUserBeanVO extends PhoneHistoryDeptBeanVO {
    private String userName;

    public PhoneHistoryUserBeanVO() {
    }

    public PhoneHistoryUserBeanVO(Object[] objects) {
        super(objects);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
