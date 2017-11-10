package com.mindata.ecserver.global.aop;

import com.mindata.ecserver.global.cache.UserTokenCache;
import com.mindata.ecserver.global.constant.Constant;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.date.TimeInterval;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
@Aspect
@Component
@Order(1)
public class LogAspect {
    @Resource
    private UserTokenCache userTokenCache;
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    private TimeInterval timer;

    @Pointcut("execution(public * com.mindata.ecserver.main.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("-------------------用户发起请求-----------------");
        logger.info("token为：" + request.getHeader(Constant.AUTHORIZATION));
        logger.info("userId为:" + userTokenCache.getUserIdByToken(request.getHeader(Constant.AUTHORIZATION)));
        timer = DateUtil.timer();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        //如果是表单，参数值是普通键值对。如果是application/json，则request.getParameter是取不到的。
        logger.info("HTTP_HEAD Type : " + request.getHeader("content-type"));
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint
                .getSignature().getName());
        logger.info("传参为：");

        if (Constant.APP_JSON.equals(request.getHeader(Constant.CONTENT_TYPE))) {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Object[] objects = joinPoint.getArgs();
            for (int i = 0; i < method.getParameters().length; i++) {
                Parameter parameter = method.getParameters()[i];
                if (parameter.isAnnotationPresent(RequestBody.class)) {
                    //记录application/json时的传参，SpringMVC中使用@RequestBody接收的值
                    logger.info(objects[i].toString());
                }
            }

        } else {
            //记录请求的键值对
            for (String key : request.getParameterMap().keySet()) {
                logger.info(key + "->" + request.getParameter(key));
            }
        }

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("方法的返回值 : " + ret);
        logger.info("共耗时：" + timer.interval() + "毫秒");
        logger.info("------------------请求结束------------------");
    }

    //后置异常通知
    @AfterThrowing(throwing = "ex", pointcut = "webLog()")
    public void throwss(JoinPoint jp, Exception ex) {
        logger.info("方法异常时执行.....");
    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp) {
//        logger.info("方法最后执行.....");
    }
}
