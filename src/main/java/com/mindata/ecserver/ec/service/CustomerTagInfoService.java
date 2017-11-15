package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.request.CustomerTagRequest;
import com.mindata.ecserver.ec.model.response.CustomerTagData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author hanliqiang wrote on 2017/11/15
 * 获取标签信息
 */
public interface CustomerTagInfoService {
    /**
     * 获取员工客户库分组信息
     *
     * @return 完整结果
     */
    @POST("label/getLabelInfo")
    Call<CustomerTagData> getLabelInfo(@Body CustomerTagRequest customerTagRequest);

}
