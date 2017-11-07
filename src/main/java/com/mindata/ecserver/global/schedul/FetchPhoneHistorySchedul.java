package com.mindata.ecserver.global.schedul;

import com.mindata.ecserver.main.service.PhoneHistoryCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天晚上1点获取昨天的通话历史
 *
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class FetchPhoneHistorySchedul {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private PhoneHistoryCompanyService phoneHistoryCompanyService;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0/2 8-20 * * ?")
    public void executeFetchPhoneHistoryTask() {
    }
}
