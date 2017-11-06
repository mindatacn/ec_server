package com.mindata.ecserver.ec.model.request;

/**
 * @author wuweifeng wrote on 2017/11/6.
 * 根据手机号请求用户信息
 */
public class UserAccountRequest {
    private String account;

    public UserAccountRequest(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
