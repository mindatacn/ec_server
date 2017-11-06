package com.mindata.ecserver.ec.model.request;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public class AppIdRequest {
    private String appId;
    private String appSecret;

    public AppIdRequest() {
    }

    public AppIdRequest(String appId, String appSecret) {
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
