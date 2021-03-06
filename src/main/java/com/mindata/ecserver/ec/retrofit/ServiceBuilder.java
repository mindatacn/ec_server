package com.mindata.ecserver.ec.retrofit;

import com.mindata.ecserver.ec.service.AuthService;
import com.mindata.ecserver.ec.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
@Component
public class ServiceBuilder {
    @Autowired
    private RetrofitBuilder retrofitBuilder;

    public AuthService getAuthService() {
        return retrofitBuilder.getRetrofit().create(AuthService.class);
    }

    public CompanyInfoService getCompanyInfoService() {
        return retrofitBuilder.getRetrofit().create(CompanyInfoService.class);
    }
}
