package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.request.ClassRequest;
import com.mindata.ecserver.ec.model.response.ClassData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author hanliqiang wrote on 2017/11/15
 * 获取标签信息
 */
public interface ClassInfoService {
    /**
     * 获取员工客户库分组信息
     *
     * @return 完整结果
     */
    @POST("label/getLabelInfo")
    Call<ClassData> getLabelInfo(@Body ClassRequest classRequest);

}
