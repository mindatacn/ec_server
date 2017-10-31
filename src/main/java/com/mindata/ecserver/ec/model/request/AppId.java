package com.mindata.ecserver.ec.model.request;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public class AppId {
    private String appId;
    private String appSecret;

    public AppId() {
    }

    public AppId(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
