package com.mindata.ecserver.global.bean;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public enum ResultCode {
    SUCCESS(200),//成功

    FAIL(400),//失败

    UNAUTHORIZED(401),//未认证（签名错误）

    NO_LOGIN(402),//没有登录

    NO_PERMISSION(403),//没有权限

    NOT_FOUND(404),//接口不存在

    EC_ERROR(405),//EC系统出现异常

    INTERNAL_SERVER_ERROR(500),  //服务器内部错误

    PARAMETER_ERROR(10001), //参数错误

    ACCOUNT_ERROR(20001),  //账号错误

    LOGIN_FAIL_ERROR(20002);  //登录失败

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
