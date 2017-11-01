package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtUserPushCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/1.
 */
public interface PtUserPushCountRepository extends JpaRepository<PtUserPushCount, Integer> {
    /**
     * 查询某用户某个时间段内推送的阈值
     */
    PtUserPushCount findByUserIdAndPushDateBetween(Integer userId, Date begin, Date end);
}
