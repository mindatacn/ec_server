package com.mindata.ecserver.global.cache;

import com.mindata.ecserver.main.event.UserRolePermissionChangeEvent;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.mindata.ecserver.global.constant.CacheConstant.CACHE_SHIRO_USER_PERMISSION_EXPIE;
import static com.mindata.ecserver.global.constant.CacheConstant.CACHE_SHIRO_USER_PERMISSION_KEY;

/**
 * @author wuweifeng wrote on 2017/10/27.
 * 用户权限信息缓存
 */
@Component
public class AuthorizationCache extends BaseCache {
    @Resource
    private PtUserManager ptUserManager;

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
                CACHE_SHIRO_USER_PERMISSION_EXPIE, TimeUnit.HOURS);
    }

    /**
     * 用户角色、权限发生变化时的回调事件
     *
     * @param event
     *         事件
     */
    @EventListener
    public void listenPermissionChange(UserRolePermissionChangeEvent event) {
        Integer userId = (Integer) event.getSource();
        remove(userId);
    }

    private void remove(Integer userId) {
        PtUser user = ptUserManager.findByUserId(userId);
        stringRedisTemplate.delete(authorKeyOfAccount(user.getAccount()));
    }

    private String authorKeyOfAccount(String account) {
        return CACHE_SHIRO_USER_PERMISSION_KEY + "_" + account;
    }
}
