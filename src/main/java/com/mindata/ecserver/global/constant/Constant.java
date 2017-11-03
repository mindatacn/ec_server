package com.mindata.ecserver.global.constant;

import org.springframework.data.domain.Sort;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public class Constant {
    public static final String SALT = "mindata";

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_MANAGER = "manager";
    public static final String ROLE_LEADER = "leader";
    public static final String ROLE_USER = "user";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APP_JSON = "application/json";

    /**
     * 记录登录密码输入错误次数
     */
    public static final String SHIRO_PASS_COUNT_KEY = "shiro_pass_count";
    /**
     * 密码错误状态保存10分钟，10分钟后就可以重新输入密码了
     */
    public static final int SHIRO_PASS_COUNT_EXPIE = 10;
    /**
     * shiro保存用户权限
     */
    public static final String SHIRO_USER_PERMISSION_KEY = "shiro_user_permission";
    /**
     * 权限保存10个小时，redis存储的user权限
     */
    public static final int SHIRO_USER_PERMISSION_EXPIE = 10;
    /**
     * 用户访问任何接口header里带的token
     */
    public static final String AUTHORIZATION = "token";
    /**
     * token的key名，key为token
     */
    public static final String USER_TOKEN_KEY = "user_token_key";
    /**
     * token的key名，key为userId
     */
    public static final String USER_TOKEN_ID_KEY = "user_token_id_key";
    /**
     * 用户登录的token缓存1个小时
     */
    public static final int USER_HEADER_TOKEN_EXPIE = 3600;

    /**
     * ec的token的key
     */
    public static final String EC_TOKEN_KEY = "ec_user_token_key";

    public static final String EC_HEADER_CROP_ID = "CORP_ID";

    public static final int PAGE_SIZE = 10;
    public static final int PAGE_NUM = 0;
    public static final Sort.Direction DIRECTION = Sort.Direction.DESC;
}
