package com.mindata.ecserver.main.vo;

/**
 * @author hanliqiang wrote on 2017/12/21
 */
public class UserVO {
    private Long userId;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 账号
     */
    private String account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
