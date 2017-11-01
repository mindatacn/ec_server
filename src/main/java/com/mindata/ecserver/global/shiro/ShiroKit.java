package com.mindata.ecserver.global.shiro;

import com.mindata.ecserver.main.model.secondary.PtUser;
import org.apache.shiro.SecurityUtils;

/**
 * @author wuweifeng wrote on 2017/11/1.
 */
public class ShiroKit {
    /**
     * 获取当前用户
     *
     * @return user
     */
    public static PtUser getCurrentUser() {
        return (PtUser) SecurityUtils.getSubject().getPrincipal();
    }
}
