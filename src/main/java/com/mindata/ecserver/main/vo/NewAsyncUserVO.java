package com.mindata.ecserver.main.vo;

import com.mindata.ecserver.main.model.secondary.PtUser;

/**
 * @author hanliqiang wrote on 2017/12/21
 */
public class NewAsyncUserVO {
    private Long userId;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    public NewAsyncUserVO(PtUser ptUser) {
        setUserId(ptUser.getId());
        setName(ptUser.getName());
        setAccount(ptUser.getAccount());
        //获取明文密码
        setPassword(ptUser.getEcUserId().toString());
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "NewAsyncUserVO{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
