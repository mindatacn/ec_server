package com.mindata.ecserver.global.shiro;

import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.mindata.ecserver.global.constant.Constant.SHIRO_USER_PERMISSION_EXPIE;
import static com.mindata.ecserver.global.constant.Constant.SHIRO_USER_PERMISSION_KEY;

/**
 * @author wuweifeng wrote on 2017/10/27.
 * 用户权限信息缓存
 */
@Component
public class AuthorizationCache {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据account获取缓存的权限信息
     *
     * @param account
     *         account
     * @return 权限集合
     */
    public SimpleAuthorizationInfo getAuthorizationInfoByAccount(String account) {
        Object object = stringRedisTemplate.opsForValue().get(authorKeyOfAccount(account));
        if (object != null) {
            return JSONUtil.toBean(new JSONObject(object), SimpleAuthorizationInfo.class);
        }
        return null;
    }

    public void setAccountAuthorizationInfo(String account, SimpleAuthorizationInfo authorizationInfo) {
        stringRedisTemplate.opsForValue().set(authorKeyOfAccount(account), JSONUtil.toJsonStr
                        (authorizationInfo),
                SHIRO_USER_PERMISSION_EXPIE, TimeUnit.HOURS);
    }

    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    private String authorKeyOfAccount(String account) {
        return SHIRO_USER_PERMISSION_KEY + "_" + account;
    }
}
