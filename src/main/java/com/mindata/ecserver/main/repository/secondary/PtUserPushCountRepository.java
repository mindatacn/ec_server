package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtAnalyContactCount;
import com.mindata.ecserver.main.model.secondary.PtUserPushCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/1.
 */
public interface PtUserPushCountRepository extends JpaRepository<PtUserPushCount, Long> {
    /**
     * 查询某用户某个时间段内推送的阈值
     */
    PtUserPushCount findByUserIdAndPushDateBetween(Long userId, Date begin, Date end);

    /**
     * @param beginTime
     * @param endTime
     * @return
     */
//    @Query(value = "SELECT pushedCount, COUNT(userId) FROM PtUserPushCount WHERE pushedCount>0 AND pushDate BETWEEN ?1 AND ?2")
//    List<Object[]> findByAnalyDateBetween(Date beginTime, Date endTime);

    @Query(value = "SELECT sum(pushedCount) FROM PtUserPushCount WHERE pushDate BETWEEN ?1 AND ?2")
    Integer findCountByPushDate(Date beginTime, Date endTime);

    List<PtUserPushCount> findByPushDateBetween(Date begin, Date end);

    @Query("SELECT sum(pushedCount),count(userId) from PtUserPushCount where pushedCount > 0 and pushDate between ?1 and ?2")
    List<Object[]> findByOneDayBetween(Date beginTime, Date endTime);
}
