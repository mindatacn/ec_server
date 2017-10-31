package com.mindata.ecserver.main.service.base;

import com.mindata.ecserver.main.model.secondary.PtUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
@Service
public class BaseService {
    /**
     * 获取当前用户
     *
     * @return user
     */
    public PtUser getCurrentUser() {
        return (PtUser) SecurityUtils.getSubject().getPrincipal();
    }

}
