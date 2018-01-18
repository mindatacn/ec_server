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
        if (ptUserManager.isErrorUser(user)) {
            return null;
        }
        //如果公司state不为0，公司所有人不可登录。如果公司产品有故障，也不可登录
        if (ptCompanyManager.isStatusError(user.getCompanyId())) {
            return null;
        }

        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        PtUser user = (PtUser) principalCollection.getPrimaryPrincipal();
        //配置权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //添加角色信息
        authorizationInfo.addRoles(userService.findRolesByUser(user));
        authorizationInfo.addStringPermissions(userService.findPermissionsByUser(user));

        return authorizationInfo;
    }
}
