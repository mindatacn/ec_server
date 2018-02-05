package com.mindata.ecserver.global.aop;

import com.mindata.ecserver.ec.model.request.AppIdRequest;
import com.mindata.ecserver.ec.model.response.AccessData;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.AuthService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.cache.EcTokenCache;
import com.mindata.ecserver.main.model.secondary.PtCompany;
import com.mindata.ecserver.main.service.CompanyService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
@Aspect
@Component
@Order(2)
public class CheckEcAspect {
    @Resource
    private EcTokenCache ecTokenCache;
    @Resource
    private CompanyService companyService;
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private CallManager callManager;

    @Around("@annotation(checkEcAnnotation)")
    public Object around(ProceedingJoinPoint pjp, CheckEcAnnotation checkEcAnnotation) throws Throwable {
        //如果该公司还未设置CorpId，直接return就好了
        if (companyService.getCorpId() == null) {
            return null;
        }
        String corpId = companyService.getCorpId() + "";
        //开始判断ec的token
        String ecToken = ecTokenCache.getTokenByCorpId(corpId);
        //没token
        if (ecToken == null) {
            AuthService authService = serviceBuilder.getAuthService();
            PtCompany company = companyService.findNowCompany();
            AppIdRequest appIdRequest = new AppIdRequest(company.getAppId(), company.getAppSecret());
            AccessData accessData = (AccessData) callManager.execute(authService.accessToken(appIdRequest));
            ecToken = accessData.getData().getAccessToken();
            ecTokenCache.setTokenByCorpId(corpId, ecToken);
        }

        return pjp.proceed();
    }

}
