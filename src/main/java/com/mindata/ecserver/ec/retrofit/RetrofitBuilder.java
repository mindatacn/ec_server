package com.mindata.ecserver.ec.retrofit;

import com.mindata.ecserver.global.cache.EcTokenCache;
import com.mindata.ecserver.main.service.CompanyService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/20.
 * ec的各个api统一配置在这里
 */
@Configuration
public class RetrofitBuilder {

    @Value("${ecServer.ecBaseUri}")
    private String ecBaseUri;
    @Resource
    private CompanyService companyService;
    @Resource
    private EcTokenCache ecTokenCache;

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(ecBaseUri)
                .client(generClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient generClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    String corpId = companyService.getCorpId() + "";
                    String authorization = ecTokenCache.getTokenByCorpId(corpId);
                    if (authorization == null) {
                        authorization = "";
                        corpId = "";
                    }
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json; charset=UTF-8")
                            //.addHeader("Accept-Encoding", "gzip, deflate")
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Accept", "*/*")
                            .addHeader("Cookie", "add cookies here")
                            .addHeader("Authorization", authorization)
                            .addHeader("CORP_ID", corpId)
                            .build();
                    return chain.proceed(request);
                }).build();
    }

}
