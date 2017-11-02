package com.mindata.ecserver.global.shiro;

import com.mindata.ecserver.main.manager.PtCompanyManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Configuration
public class StatelessAuthorizingRealm extends AuthorizingRealm {
    @Resource
    private PtUserManager ptUserManager;
    @Resource
    private PtCompanyManager ptCompanyManager;
    @Resource
    private UserService userService;
    @Resource
    private AuthorizationCache cache;

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     * @param authenticationToken
     *         token
     * @return 身份信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws
            AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        PtUser user = ptUserManager.findByAccount(token.getUsername());
        //用户不存在或者已被删除
        if (null == user || user.getState() != 0) {
            return null;
        }
        //如果公司state不为0，公司所有人不可登录
        if (user.getCompanyId() != 0 && ptCompanyManager.findOne(user.getCompanyId()).getStatus() != 0) {
            return null;
        }

        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        PtUser user = (PtUser) principalCollection.getPrimaryPrincipal();
        String key = user.getAccount();
        //从redis获取缓存的信息
        SimpleAuthorizationInfo authorizationInfo = cache.getAuthorizationInfoByAccount(key);
        if (authorizationInfo != null) {
            return authorizationInfo;
        }
        //配置权限
        authorizationInfo = new SimpleAuthorizationInfo();

        authorizationInfo.addRoles(userService.findRolesByUser(user));
        authorizationInfo.addStringPermissions(userService.findPermissionsByUser(user));
        //将权限缓存到redis
        cache.setAccountAuthorizationInfo(key, authorizationInfo);

        return authorizationInfo;
    }
}
