package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtSearchConditionManager;
import com.mindata.ecserver.main.model.secondary.PtSearchCondition;
import com.mindata.ecserver.main.requestbody.ContactRequestBody;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/21.
 */
@Service
public class SearchConditionService {
    @Resource
    private PtSearchConditionManager ptSearchConditionManager;

    /**
     * 添加一条记录
     *
     * @param contactRequestBody
     *         请求体
     * @return 插入成功记录
     */
    public PtSearchCondition add(ContactRequestBody contactRequestBody) {
        Long userId = ShiroKit.getCurrentUserId();
        return ptSearchConditionManager.add(contactRequestBody, userId);
    }

}
