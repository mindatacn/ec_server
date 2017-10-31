package com.mindata.ecserver.ec.service;

import com.mindata.ecserver.ec.model.response.CompanyData;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author wuweifeng wrote on 2017/10/30.
 * 同步公司信息（公司、部门、员工）
 */
public interface CompanyInfoService {
    /**
     * 获取公司的部门、人员信息
     *
     * @return 完整结果
     */
    @GET("user/structure")
    Call<CompanyData> getCompanyInfo();

}
