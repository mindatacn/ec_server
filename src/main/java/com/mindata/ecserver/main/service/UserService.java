package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.request.UserAccountRequest;
import com.mindata.ecserver.ec.model.response.UserAccountData;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.UserAccountService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.global.bean.TokenExpire;
import com.mindata.ecserver.global.cache.UserTokenCache;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.*;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.model.secondary.PtUserPushCount;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.NewAsyncUserVO;
import com.mindata.ecserver.main.vo.RoleVO;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.shiro.ShiroKit.getCurrentUser;

/**
 * @author wuweifeng wrote on 2017/10/27.
 */
@Service
public class UserService extends BaseService {
    @Resource
    private PtRoleManager roleManager;
    @Resource
    private PtUserManager userManager;
    @Resource
    private PtUserPushCountManager ptUserPushCountManager;
    @Resource
    private UserTokenCache userTokenCache;
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private CallManager callManager;
    @Resource
    private PtRoleMenuManager ptRoleMenuManager;
    @Resource
    private PtUserRoleManager ptUserRoleManager;

    /**
     * 查询用户的所有权限集合
     *
     * @param user user
     * @return 权限集合
     */
    public Set<String> findPermissionsByUser(PtUser user) {
        List<PtRole> roles = roleManager.findRolesByUser(user);
        List<PtMenu> menus = ptRoleMenuManager.findAllMenuByRoles(roles);
        return menus.stream().map(PtMenu::getPermission).collect(Collectors.toSet());
    }

    /**
     * 获取用户的所有角色字符串
     *
     * @param user user
     * @return 用户角色
     */
    public Set<String> findRolesByUser(PtUser user) {
        //从缓存获取
        List<PtRole> roles = roleManager.findRolesByUser(user);
        return roles.stream().map(PtRole::getName).collect(Collectors.toSet());
    }

    /**
     * 获取用户的角色
     *
     * @return 角色
     */
    public List<RoleVO> findRole() {
        List<PtRole> roles = roleManager.findRolesByUser(getCurrentUser());
        return roles.stream().map(ptRole -> new RoleVO(ptRole.getName())).collect(Collectors.toList());
    }

    public PtUser getInfo() {
        PtUser ptUser = getCurrentUser();
        ptUser.setRoles(findRole());
        return ptUser;
    }

    /**
     * 根据token获取user
     *
     * @param token token
     * @return User
     */
    public PtUser findUserByHeaderToken(String token) {
        String userIdStr = userTokenCache.getUserIdByToken(token);
        if (userIdStr == null) {
            return null;
        }
        return userManager.findByUserId(Long.valueOf(userIdStr));
    }

    /**
     * 给user生成访问token
     *
     * @return token
     */
    public TokenExpire token() {
        PtUser ptUser = getCurrentUser();
        Long userId = ptUser.getId();
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
        Long userId = ptUser.getId();
        //获得token
        String token = userTokenCache.getTokenByUserId(userId);
        userTokenCache.setBothTokenByUserId(token, userId);

        return new TokenExpire(token, userTokenCache.getExpire(userId));
    }

    /**
     * 修改密码
     */
    public int modifyPassword(String oldPassword, String newPassword) {
        oldPassword = CommonUtil.password(oldPassword);
        PtUser ptUser = getCurrentUser();
        //原密码输入错误
        if (!oldPassword.equals(ptUser.getPassword())) {
            return -1;
        }
        newPassword = CommonUtil.password(newPassword);
        ptUser.setPassword(newPassword);
        userManager.update(ptUser);
        return 0;
    }

    /**
     * 修改个人信息
     *
     * @param name   名字
     * @param mobile 电话
     * @param email  邮箱
     * @return user
     */
    public PtUser modifyInfo(String name, String mobile, String email) {
        PtUser ptUser = getCurrentUser();
        if (name != null && !StrUtil.equals(name, ptUser.getName())) {
            ptUser.setName(name);
        }
        if (mobile != null && !StrUtil.equals(mobile, ptUser.getMobile())) {
            ptUser.setMobile(mobile);
        }
        if (email != null && !StrUtil.equals(email, ptUser.getEmail())) {
            ptUser.setEmail(email);
        }
        return userManager.update(ptUser);
    }

    /**
     * 绑定ec账号
     */
    public int bindEc(String phone) throws IOException {
        UserAccountService userAccountService = serviceBuilder.getUserAccountService();
        UserAccountRequest userAccountRequest = new UserAccountRequest(phone);
        UserAccountData userAccountData = (UserAccountData) callManager.execute(userAccountService.userAccount
                (userAccountRequest));
        //得到ec的userId
        Long ecUserId = userAccountData.getData().getF_user_id();
        //判断是否在本系统中已经被占用
        PtUser user = userManager.findByEcUserId(ecUserId);
        if (user != null) {
            return -1;
        }

        PtUser ptUser = ShiroKit.getCurrentUser();
        ptUser.setEcUserId(ecUserId);
        userManager.update(ptUser);

        return 0;
    }

    /**
     * 退出登录清除token信息
     */
    public void logout() {
        PtUser ptUser = getCurrentUser();
        //该user的token已经存在了
        userTokenCache.deleteBothByUserId(ptUser.getId());
    }

    /**
     * 查询自己部门或公司的所有员工name like的集合
     *
     * @return 结果集
     */
    public List<PtUser> findByNameLike(String name) {
        PtUser ptUser = ShiroKit.getCurrentUser();
        if (roleManager.isManager(ptUser.getId())) {
            return userManager.findByCompanyIdAndNameLike(ptUser.getCompanyId(), name);
        }
        return userManager.findByDeptIdAndNameLike(ptUser.getDepartmentId(), name);
    }

    /**
     * 查询今天的推送数量
     *
     * @return 类
     */
    public PtUserPushCount findPushCount() {
        return ptUserPushCountManager.findCountByUserId(ShiroKit.getCurrentUser().getId()
                , null);
    }

    /**
     * 查询同步后新增的用户信息
     * @param beginId 参数，不包含
     * @param endId 参数，包含
     * @return 结果
     */
    public List<NewAsyncUserVO> findByIdBetween(Long beginId, Long endId, Long companyId) {
        // 如果同步之前的最大id和同步之后的最大id一样 则没有新数据
        if (endId <= beginId) {
            return new ArrayList<>();
        }
        List<PtUser> ptUsers = userManager.findByIdBetweenAndCompanyId(beginId + 1, endId, companyId);
        return ptUsers.stream().map(NewAsyncUserVO::new).collect(Collectors.toList());
    }

    /**
     * 修改用户推送的阈值
     *
     * @param userId    用户id
     * @param threshold 阈值
     */
    public void updateThresholdByUserId(Long userId, Integer threshold) {
        userManager.updateThresholdByUserId(userId, threshold);
    }

    /**
     * 根据公司id查找管理员账号
     * @param companyId companyId
     * @return PtUser
     */
    public PtUser findManagerUser(Long companyId){
        return userManager.findManagerByCompanyId(companyId);
    }
}
