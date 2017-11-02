package com.mindata.ecserver.global.shiro;

import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.service.UserService;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mindata.ecserver.global.bean.ResultCode.NO_LOGIN;
import static com.mindata.ecserver.global.constant.Constant.AUTHORIZATION;

/**
 * @author wuweifeng wrote on 2017/10/27.
 * 访问控制过滤器，该过滤器执行顺序高于MvcInterceptor
 */
@Component
public class StatelessAccessControlFilter extends FormAuthenticationFilter {
    @Resource
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；
     * 如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse response) throws Exception {
        HttpServletRequest request = (HttpServletRequest) req;
        //测试相关的不拦截
        if (request.getRequestURI().startsWith("/test")) {
            return true;
        }
        //如果是登录请求
        if ("/login".equals(request.getRequestURI())) {
            return !this.isLoginSubmission(request, response) || this.executeLogin(request, response);
        }
        //校验header
        String token = request.getHeader(AUTHORIZATION);
        logger.info("token为：" + token);
        logger.info("content-type为：" + request.getHeader("content-type"));
        if (token == null) {
            gotoLogin(response);
            return false;
        }
        PtUser user = userService.findUserByHeaderToken(token);
        if (user == null) {
            gotoLogin(response);
            return false;
        }
        //header不为空，拿着token去获取user
        UsernamePasswordToken token1 = new UsernamePasswordToken(user.getAccount(), user.getPassword());
        //5、委托给Realm进行登录
        getSubject(request, response).login(token1);
        return true;
    }

    private void gotoLogin(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write(JSONUtil.toJsonStr(JSONUtil.parse(ResultGenerator.genFailResult(NO_LOGIN,
                "you have not login"))));
    }

}
