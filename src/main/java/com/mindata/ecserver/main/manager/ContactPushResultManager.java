package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.repository.secondary.EcContactPushResultRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 已推送的信息管理
 */
@Service
public class ContactPushResultManager {
    @Resource
    private EcContactPushResultRepository contactPushResultRepository;

    /**
     * 查看一段时间内的推送总数量
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 总数量
     */
    public int countAllByDate(Date begin, Date end) {
        return contactPushResultRepository.countByPushTimeBetween(begin, end);
    }

    /**
     * 查看某段时间且状态符合的总数量
     *
     * @param status
     *         状态（0，1）
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 数量
     */
    public int countByStatusAndDate(int status, Date begin, Date end) {
        return contactPushResultRepository.countByStatusAndPushTimeBetween(status, begin, end);
    }
}
