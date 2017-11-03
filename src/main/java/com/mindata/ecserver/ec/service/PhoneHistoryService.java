package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.request.PhoneHistoryRequest;
import com.mindata.ecserver.ec.model.response.PhoneHistory;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author wuweifeng wrote on 2017/11/3.
 */
public interface PhoneHistoryService {
    /**
     * 获取
     *
     * @return token
     */
    @POST("record/telRecord")
    Call<PhoneHistory> history(@Body PhoneHistoryRequest phoneHistoryRequest);
}
