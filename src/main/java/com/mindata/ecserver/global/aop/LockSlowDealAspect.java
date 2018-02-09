package com.mindata.ecserver.global.aop;

import com.mindata.ecserver.global.annotation.LockSlowDealAnnotation;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.shiro.ShiroKit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wuweifeng wrote on 2018/2/9.
 */
@Aspect
@Component
@Order(3)
public class LockSlowDealAspect {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Around("@annotation(lockSlowDealAnnotation)")
    public Object around(ProceedingJoinPoint pjp, LockSlowDealAnnotation lockSlowDealAnnotation) throws Throwable {
        //如果该公司的该模块正在进行耗时操作，则校验操作是否进行中，如果进行中，则拒绝该接口的请求
        Long companyId = ShiroKit.getCurrentCompanyId();
        String key = lockSlowDealAnnotation.moduleName() + companyId;
        //不存在则set为1，如果成功了则取到锁
        boolean lock = redisTemplate.opsForValue().setIfAbsent(key, "1");
        if (lock) {
            redisTemplate.opsForValue().set(key, "1", 5, TimeUnit.MINUTES);
            Object object = pjp.proceed();
            redisTemplate.delete(key);
            return object;
        } else {
            return ResultGenerator.genFailResult("该接口繁忙，稍后再试");
        }

    }
}
