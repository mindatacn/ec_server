package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.base.BaseEcData;
import com.mindata.ecserver.ec.model.request.UserTrajectoryRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 批量添加跟进记录
 *
 * @author wuweifeng wrote on 2017/11/28.
 */
public interface UserTrajectoryService {
    /**
     * 获取token
     *
     * @return token
     */
    @POST("trajectory/saveUserTrajectory")
    Call<BaseEcData> batchCreate(@Body UserTrajectoryRequest userTrajectoryRequest);
}
