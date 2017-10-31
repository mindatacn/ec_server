package com.mindata.ecserver.ec.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuweifeng wrote on 2017/10/20.
 * ec的各个api统一配置在这里
 */
@Configuration
public class RetrofitBuilder {

    @Value("${ecServer.ecBaseUri}")
    private String ecBaseUri;

    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ecBaseUri)
                .client(generClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient generClient() {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Object authorization = httpServletRequest.getAttribute("Authorization");
                    Object corpId = httpServletRequest.getAttribute("CORP_ID");
                    if (authorization == null || corpId == null) {
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
                            .addHeader("Authorization", authorization.toString())
                            .addHeader("CORP_ID", corpId.toString())
                            .build();
                    return chain.proceed(request);
                }).build();
    }

}
