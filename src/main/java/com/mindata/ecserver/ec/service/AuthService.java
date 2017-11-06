package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.request.AppIdRequest;
import com.mindata.ecserver.ec.model.response.AccessData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public interface AuthService {
    /**
     * 获取token
     *
     * @return token
     */
    @POST("auth/accesstoken")
    Call<AccessData> accessToken(@Body AppIdRequest appIdRequest);
}
