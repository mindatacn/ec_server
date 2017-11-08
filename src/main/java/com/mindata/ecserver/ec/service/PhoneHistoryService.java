package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.request.PhoneFarHistoryRequest;
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
     * 获取一个月以内的通话历史
     */
    @POST("record/telRecord")
    Call<PhoneHistory> history(@Body PhoneHistoryRequest phoneHistoryRequest);

    /**
     * 获取更早期的通话历史
     */
    @POST("record/telRecordHistory")
    Call<PhoneHistory> farHistory(@Body PhoneFarHistoryRequest phoneHistoryRequest);
}
