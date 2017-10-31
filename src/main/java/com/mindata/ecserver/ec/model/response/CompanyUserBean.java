package com.mindata.ecserver.ec.model.response;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
public class CompanyUserBean {
    private Integer userId;
    private Integer deptId;
    private String userName;
    private String account;
    private String title;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CompanyUserBean{" +
                "userId=" + userId +
                ", deptId=" + deptId +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
