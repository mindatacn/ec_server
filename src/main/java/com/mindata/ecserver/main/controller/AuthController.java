package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.ec.model.request.AppIdRequest;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.AuthService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultCode;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.exception.NoLoginException;
import com.mindata.ecserver.main.service.UserService;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

import static com.mindata.ecserver.global.bean.ResultCode.ACCOUNT_ERROR;
import static com.mindata.ecserver.global.bean.ResultCode.PARAMETER_ERROR;

/**
 * @author wuweifeng wrote on 2017/10/23.
 * 认证登录
 */
@RestController
public class AuthController {
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private CallManager callManager;

    @Resource
    private UserService userService;

    @RequestMapping("/ec/token")
    public Object token() throws IOException {
        AuthService authService = serviceBuilder.getAuthService();
        AppIdRequest appIdRequest = new AppIdRequest("138300510292672512", "DUPsunmpVFNKqAId6hI");

        return callManager.execute(authService.accessToken(appIdRequest));
    }

    @RequestMapping({"/403"})
    public BaseData noauth() {
        return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED, "未认证");
    }

    @RequestMapping({"/noLogin"})
    public BaseData noLogin() {
        throw new NoLoginException();
    }

    @GetMapping("/refreshToken")
    public BaseData getToken() {
        return ResultGenerator.genSuccessResult(userService.refreshToken());
    }

    /**
     * 账号密码登录
     *
     * @param account
     *         账号
     * @param password
     *         密码
     * @return 登录成功失败
     */
    @PostMapping("/login")
    public BaseData login(String account, String password) {
        if (StrUtil.isEmpty(password) || password.length() < 6) {
            return ResultGenerator.genFailResult(PARAMETER_ERROR, "参数错误");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(account, CommonUtil.password(password));
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            return ResultGenerator.genFailResult(ACCOUNT_ERROR, "账户不存在");
        } catch (IncorrectCredentialsException ice) {
            return ResultGenerator.genFailResult(ACCOUNT_ERROR, "密码不正确");
        } catch (ExcessiveAttemptsException lae) {
            return ResultGenerator.genFailResult(ACCOUNT_ERROR, "密码错误次数过多，账户已锁定");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            //给用户创建token，放到redis
            return ResultGenerator.genSuccessResult(userService.token());
        } else {
            token.clear();
            return ResultGenerator.genFailResult("");
        }
    }

    @GetMapping(value = "/logout")
    public BaseData logout() {
        //使用权限管理工具进行用户的退出，跳出登录
        SecurityUtils.getSubject().logout();
        userService.logout();
        return ResultGenerator.genSuccessResult();
    }

}
