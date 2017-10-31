package com.mindata.ecserver.global.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2017/10/26.
 * 参考http://412887952-qq-com.iteye.com/blog/2359097</p>
 * shiro无状态配置，密码连续错误配置
 */
@Configuration
public class ShiroConfig {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.getFilters().put("authc", statelessAuthcFilter());

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/noLogin");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 设置realm.
        securityManager.setRealm(myShiroRealm());

        //禁用session
        securityManager.setSubjectFactory(subjectFactory());
        securityManager.setSessionManager(sessionManager());
        //禁用使用Sessions 作为存储策略的实现，但它没有完全地禁用Sessions
        // 所以需要配合context.setSessionCreationEnabled(false)
        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO())
                .getSessionStorageEvaluator()).
                setSessionStorageEnabled(false);

        return securityManager;
    }

    /**
     * subject工厂管理器
     */
    @Bean
    public DefaultWebSubjectFactory subjectFactory() {
        return new StatelessDefaultSubjectFactory();
    }

    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     *         securityManager
     * @return advisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager
                                                                                           securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new
                AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public StatelessAccessControlFilter statelessAuthcFilter() {
        return new StatelessAccessControlFilter();
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     *
     * @return
     */
    @Bean
    public StatelessAuthorizingRealm myShiroRealm() {
        StatelessAuthorizingRealm statelessAuthorizingRealm = new StatelessAuthorizingRealm();
        //设置密码校验的规则，可以为hash、md5等等，我这里匹配规则为密码直接比较，并限定错误次数
        statelessAuthorizingRealm.setCredentialsMatcher(new MyMatcher(stringRedisTemplate));
        return statelessAuthorizingRealm;
    }

}