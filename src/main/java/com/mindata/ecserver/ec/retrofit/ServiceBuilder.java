package com.mindata.ecserver.ec.retrofit;

import com.mindata.ecserver.ec.service.*;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
@Component
public class ServiceBuilder {
    @Resource
    private Retrofit retrofit;

    public AuthService getAuthService() {
        return retrofit.create(AuthService.class);
    }

    public CompanyInfoService getCompanyInfoService() {
        return retrofit.create(CompanyInfoService.class);
    }

    public CustomerService getCustomerService() {
        return retrofit.create(CustomerService.class);
    }

    public PhoneHistoryService getPhoneHistoryService() {
        return retrofit.create(PhoneHistoryService.class);
    }

    public UserAccountService getUserAccountService() {
        return retrofit.create(UserAccountService.class);
    }

    public CustomerGroupInfoService getCustomerGroupInfoService(){
        return retrofit.create(CustomerGroupInfoService.class);
    }

    public CustomerTagInfoService getClassInfoService(){
        return retrofit.create(CustomerTagInfoService.class);
    }

    public UserTrajectoryService getUserTrajectoryService() {
        return retrofit.create(UserTrajectoryService.class);
    }
}
