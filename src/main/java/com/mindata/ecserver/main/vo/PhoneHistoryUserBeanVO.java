package com.mindata.ecserver.main.vo;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class PhoneHistoryUserBeanVO extends PhoneHistoryDeptBeanVO {
    private String userName;
    private Long userId;

    public PhoneHistoryUserBeanVO() {
    }

    public PhoneHistoryUserBeanVO(Object[] objects) {
        super(objects);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "PhoneHistoryUserBeanVO{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
