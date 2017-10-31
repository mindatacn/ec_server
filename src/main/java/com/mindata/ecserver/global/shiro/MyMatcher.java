package com.mindata.ecserver.global.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static com.mindata.ecserver.global.constant.Constant.SHIRO_PASS_COUNT_EXPIE;
import static com.mindata.ecserver.global.constant.Constant.SHIRO_PASS_COUNT_KEY;

/**
 * @author wuweifeng wrote on 2017/10/27.
 * 配置密码连续输错多少次就抛异常
 */
public class MyMatcher extends PasswordMatcher {
    private StringRedisTemplate redisTemplate;
    private ValueOperations valueOperations;
    private static final int MAX_COUNT = 5;

    public MyMatcher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取用户名
        String username = (String) token.getPrincipal();
        int count = 0;
        //获取用户登录的次数
        Object retryCount = valueOperations.get(SHIRO_PASS_COUNT_KEY + "_" + username);
        //如果用户未登陆过
        if (retryCount != null) {
            count = Integer.valueOf(retryCount.toString());
        }
        count++;

        //如果用户登录次数超过三次（此处可根据需要自定义）
        if (count > MAX_COUNT) {
            //抛出用户锁定异常类
            throw new ExcessiveAttemptsException();
        }
        valueOperations.set(SHIRO_PASS_COUNT_KEY + "_" + username, count + "", SHIRO_PASS_COUNT_EXPIE, TimeUnit
                .MINUTES);

        //判断用户是否可用，即是否为正确的账号密码
        boolean matches = new String((char[]) token.getCredentials()).equals(info.getCredentials());
        if (matches) {
            //移除缓存中用户的登录次数
            redisTemplate.delete(SHIRO_PASS_COUNT_KEY + "_" + username);
        }

        return matches;
    }

}