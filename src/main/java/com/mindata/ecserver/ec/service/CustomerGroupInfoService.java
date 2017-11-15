package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.request.CustomerGroupRequest;
import com.mindata.ecserver.ec.model.response.CustomerGroupData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author hanliqiang wrote on 2017/11/15
 * 获取员工客户库分组信息
 */
public interface CustomerGroupInfoService {
    /**
     * 获取员工客户库分组信息
     * @return 完整结果
     */
    @POST("customer/getCustomerGroup")
    Call<CustomerGroupData> getCustomerGroupInfo(@Body CustomerGroupRequest customerGroupRequest);

}
