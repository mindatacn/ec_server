package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.request.CustomerCreateRequest;
import com.mindata.ecserver.ec.model.response.CustomerCreateData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public interface CustomerService {
    /**
     * 获取
     *
     * @return token
     */
    @POST("customer/create")
    Call<CustomerCreateData> batchCreate(@Body CustomerCreateRequest customerCreateRequest);
}
