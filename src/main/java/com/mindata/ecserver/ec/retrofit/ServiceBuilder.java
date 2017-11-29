package com.mindata.ecserver.ec.retrofit;

import com.mindata.ecserver.ec.service.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
@Component
public class ServiceBuilder {
    @Resource
    private RetrofitBuilder retrofitBuilder;

    public AuthService getAuthService() {
        return retrofitBuilder.getRetrofit().create(AuthService.class);
    }

    public CompanyInfoService getCompanyInfoService() {
        return retrofitBuilder.getRetrofit().create(CompanyInfoService.class);
    }

    public CustomerService getCustomerService() {
        return retrofitBuilder.getRetrofit().create(CustomerService.class);
    }

    public PhoneHistoryService getPhoneHistoryService() {
        return retrofitBuilder.getRetrofit().create(PhoneHistoryService.class);
    }

    public UserAccountService getUserAccountService() {
        return retrofitBuilder.getRetrofit().create(UserAccountService.class);
    }

    public CustomerGroupInfoService getCustomerGroupInfoService(){
        return retrofitBuilder.getRetrofit().create(CustomerGroupInfoService.class);
    }

    public CustomerTagInfoService getClassInfoService(){
        return retrofitBuilder.getRetrofit().create(CustomerTagInfoService.class);
    }

    public UserTrajectoryService getUserTrajectoryService() {
        return retrofitBuilder.getRetrofit().create(UserTrajectoryService.class);
    }
}
