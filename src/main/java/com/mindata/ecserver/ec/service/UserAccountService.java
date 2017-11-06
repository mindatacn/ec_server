package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.request.UserAccountRequest;
import com.mindata.ecserver.ec.model.response.UserAccountData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 绑定EC账号功能
 *
 * @author wuweifeng wrote on 2017/10/23.
 */
public interface UserAccountService {
    /**
     * 获取token
     *
     * @return token
     */
    @POST("user/findUserInfoById")
    Call<UserAccountData> userAccount(@Body UserAccountRequest userAccountRequest);
}
