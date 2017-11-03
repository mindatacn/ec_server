package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 电话历史记录
 */
@Service
public class PhoneHistoryService {

    @Transactional(rollbackFor = Exception.class)
    public SimplePage fetch(Integer deptId, Integer userId, Date begin, Date end) {
        return null;
    }
}
