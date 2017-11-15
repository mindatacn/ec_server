package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtPhoneHistoryUserManager;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 电话历史记录
 */
@Service
public class PhoneHistoryService {
    @Resource
    private PtPhoneHistoryUserManager ptPhoneHistoryUserManager;

    @Transactional(rollbackFor = Exception.class)
    public SimplePage fetch(Integer deptId, Integer userId, Date begin, Date end) {
        return null;
    }

    /**
     * 查询某个用户自己的统计历史
     */
    public SimplePage fetchUserHistory(Date begin, Date end, Pageable pageable) {
        Long userId = ShiroKit.getCurrentUser().getId();
        Date tempBegin = DateUtil.beginOfDay(begin);
        Date tempEnd = DateUtil.endOfDay(end);
        Page page = ptPhoneHistoryUserManager.findByUserId(userId, begin, end, pageable);
        return null;
    }
}
