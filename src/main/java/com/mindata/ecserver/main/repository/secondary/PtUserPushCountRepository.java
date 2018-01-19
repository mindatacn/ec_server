package com.mindata.ecserver.main.repository.secondary;

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


    List<PtUserPushCount> findByPushDateBetween(Date begin, Date end);

    /**
     * 查询每天的统计
     *
     * @param beginTime 开始
     * @param endTime   结束
     * @return 统计
     */
    @Query("SELECT sum(pushedCount),count(userId) from PtUserPushCount where pushedCount > 0 and pushDate between ?1 and ?2")
    List<Object[]> findByOneDayBetween(Date beginTime, Date endTime);

    /**
     * 查询某公司当天推送的总和
     *
     * @param companyId companyId
     * @param beginTime beginTime
     * @param endTime   endTime
     * @return Integer
     */
    @Query("SELECT sum(pushedCount) from PtUserPushCount where companyId=?1 and pushDate between ?2 and ?3")
    Integer getPushedCountSum(Long companyId, Date beginTime, Date endTime);

}
