package com.mindata.ecserver.global.cache;

import com.mindata.ecserver.global.constant.Constant;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static com.mindata.ecserver.global.constant.Constant.USER_HEADER_TOKEN_EXPIE;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
@Component
public class EcTokenCache {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String getTokenByCorpId(String corpId) {
        return stringRedisTemplate.opsForValue().get(Constant.EC_TOKEN_KEY + "_" + corpId);
    }

    public void setTokenByCorpId(String corpId, String token) {
        stringRedisTemplate.opsForValue().set(Constant.EC_TOKEN_KEY + "_" + corpId, token, USER_HEADER_TOKEN_EXPIE *
                2, TimeUnit.SECONDS);
    }

    public Long getExpire(String corpId) {
        return stringRedisTemplate.getExpire(Constant.EC_TOKEN_KEY + "_" + corpId);
    }
}
