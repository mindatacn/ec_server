package com.mindata.ecserver.global.cache;

import com.mindata.ecserver.global.constant.Constant;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wuweifeng wrote on 2017/10/30.
 * 用户登录token的缓存管理
 */
@Component
public class UserTokenCache {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void setBothTokenByUserId(String token, Integer userId) {
        stringRedisTemplate.opsForValue().set(keyOfUserId(userId), token, Constant
                .USER_HEADER_TOKEN_EXPIE, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(keyOfToken(token), userId + "", Constant
                .USER_HEADER_TOKEN_EXPIE, TimeUnit.SECONDS);
    }

    public String getTokenByUserId(Integer userId) {
        return stringRedisTemplate.opsForValue().get(keyOfUserId(userId));
    }

    public String getUserIdByToken(String token) {
        return stringRedisTemplate.opsForValue().get(keyOfToken(token));
    }

    public void deleteBothByUserId(Integer userId) {
        stringRedisTemplate.delete(keyOfUserId(userId));
        stringRedisTemplate.delete(keyOfToken(getTokenByUserId(userId)));
    }

    public Long getExpire(Integer userId) {
        return stringRedisTemplate.getExpire(keyOfUserId(userId));
    }

    private String keyOfUserId(Integer userId) {
        return Constant.USER_TOKEN_ID_KEY + "_" + userId;
    }

    private String keyOfToken(String token) {
        return Constant.USER_TOKEN_KEY + "_" + token;
    }
}
