package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.TokenExpire;
import com.mindata.ecserver.global.cache.UserTokenCache;
import com.mindata.ecserver.main.manager.PtMenuManager;
import com.mindata.ecserver.main.manager.PtRoleManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/10/27.
 */
@Service
public class UserService extends BaseService {
    @Resource
    private PtRoleManager roleManager;
    @Resource
    private PtMenuManager menuManager;
    @Resource
    private PtUserManager userManager;
    @Resource
    private UserTokenCache userTokenCache;

    /**
     * 查询用户的所有权限集合
     *
     * @param user
     *         user
     * @return 权限集合
     */
    public Set<String> findPermissionsByUser(PtUser user) {
        List<PtRole> roles = roleManager.findByUserId(user.getId());
        List<PtMenu> menus = menuManager.findAllByRoles(roles);
        return menus.stream().map(PtMenu::getPermission).collect(Collectors.toSet());
    }

    public Set<String> findRolesByUser(PtUser user) {
        List<PtRole> roles = roleManager.findByUserId(user.getId());
        return roles.stream().map(PtRole::getName).collect(Collectors.toSet());
    }

    /**
     * 根据token获取user
     *
     * @param token
     *         token
     * @return User
     */
    public PtUser findUserByHeaderToken(String token) {
        String userIdStr = userTokenCache.getUserIdByToken(token);
        if (userIdStr == null) {
            return null;
        }
        return userManager.findByUserId(Integer.valueOf(userIdStr));
    }

    /**
     * 给user生成访问token
     *
     * @return token
     */
    public TokenExpire token() {
        PtUser ptUser = getCurrentUser();
        Integer userId = ptUser.getId();
        //获得token
        String token = userTokenCache.getTokenByUserId(userId);
        if (token == null) {
            //生成token
            token = CommonUtil.token();
            userTokenCache.setBothTokenByUserId(token, userId);
        }

        return new TokenExpire(token, userTokenCache.getExpire(userId));
    }

    /**
     * 刷新当前用户的token及过期时间
     *
     * @return token
     */
    public TokenExpire refreshToken() {
        PtUser ptUser = getCurrentUser();
        Integer userId = ptUser.getId();
        //获得token
        String token = userTokenCache.getTokenByUserId(userId);
        userTokenCache.setBothTokenByUserId(token, userId);

        return new TokenExpire(token, userTokenCache.getExpire(userId));
    }


    /**
     * 退出登录清除token信息
     */
    public void logout() {
        PtUser ptUser = getCurrentUser();
        //该user的token已经存在了
        userTokenCache.deleteBothByUserId(ptUser.getId());
    }
}
