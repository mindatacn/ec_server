package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPhoneHistoryRepository extends JpaRepository<PtPhoneHistory, Long> {
    Page<PtPhoneHistory> findByStartTimeBetween(Date begin, Date end, Pageable pageable);


    /**
     * 统计某段时间、某人总的打电话次数
     */
    @Query("select count(id) , sum(callTime) , count(distinct callToNo) " +
            " from PtPhoneHistory where ecUserId = ?1 and startTime between ?2 and ?3")
    List<Object[]> findCount(Long ecUserId, Date begin, Date end);

    /**
     * 查询某段时间通话大于多少秒的总数量
     *
     * @param seconds
     *         秒数
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 总数量
     */
    @Query("select count(distinct crmId) from PtPhoneHistory where callTime > ?1 and startTime between ?2 and ?3")
    Long countCallTimeGreaterThanAndStartTimeBetween(Integer seconds, Date begin, Date end);

    List<PtPhoneHistory> findByEcUserIdAndStartTimeBetween(Long ecUserId, Date begin, Date end);

    /**
     * 查询某个客户的累计沟通时长
     *
     * @param crmId
     *         客户id
     * @return 总时长
     */
    @Query("select  sum(callTime) from PtPhoneHistory where crmId = ?1")
    Integer findTotalContactTimeByCrmId(Long crmId);

    /**
     * 查询某个客户最后沟通时间
     *
     * @param crmId
     *         客户
     * @param pageable
     *         目前是只取一条
     * @return 结果
     */
    List<PtPhoneHistory> findByCrmIdOrderByCallTimeDesc(Long crmId, Pageable pageable);

    /**
     * 查询某天虚假的电话记录
     *
     * @param ecUserId
     *         ecUserId
     * @param begin
     *         某天的开始
     * @param end
     *         某天的结束
     * @return 集合
     */
    List<PtPhoneHistory> findByEcUserIdAndRealRecodeFalseAndStartTimeBetween(Long ecUserId, Date begin, Date end);
}
