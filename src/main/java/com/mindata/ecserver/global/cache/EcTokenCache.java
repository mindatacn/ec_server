package com.mindata.ecserver.global.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.mindata.ecserver.global.constant.CacheConstant.CACHE_EC_TOKEN_KEY;
import static com.mindata.ecserver.global.constant.CacheConstant.CACHE_USER_HEADER_TOKEN_EXPIE;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
@Component
public class EcTokenCache extends BaseCache {

    public String getTokenByCorpId(String corpId) {
        return stringRedisTemplate.opsForValue().get(CACHE_EC_TOKEN_KEY + "_" + corpId);
    }

    public void setTokenByCorpId(String corpId, String token) {
        //缓存2小时
        stringRedisTemplate.opsForValue().set(CACHE_EC_TOKEN_KEY + "_" + corpId, token, CACHE_USER_HEADER_TOKEN_EXPIE *
                2, TimeUnit.SECONDS);
    }

    public Long getExpire(String corpId) {
        return stringRedisTemplate.getExpire(CACHE_EC_TOKEN_KEY + "_" + corpId);
    }
}
