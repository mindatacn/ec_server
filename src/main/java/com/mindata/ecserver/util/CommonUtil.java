package com.mindata.ecserver.util;


import com.mindata.ecserver.global.constant.Constant;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public class CommonUtil {
    public static Date getNow() {
        return new Date();
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String token() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取加密后的密码
     *
     * @return 密码
     */
    public static String password(String oldPass) {
        Object simpleHash = new SimpleHash("MD5", oldPass, Constant.SALT, 2);
        return simpleHash.toString();
    }

    /**
     * 将object转为long
     *
     * @param object
     *         object
     * @return long
     */
    public static Long parseObject(Object object) {
        if (object instanceof Integer) {
            return ((Integer) object).longValue();
        }
        if (object instanceof Long) {
            return (Long) object;
        }
        return 0L;
    }

    public static boolean isMobile(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(password("admin"));
    }
}
