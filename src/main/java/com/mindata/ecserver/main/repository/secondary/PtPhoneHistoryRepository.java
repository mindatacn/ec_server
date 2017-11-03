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
public interface PtPhoneHistoryRepository extends JpaRepository<PtPhoneHistory, Integer> {
    Page<PtPhoneHistory> findByStartTimeBetween(Date begin, Date end, Pageable pageable);

    Page<PtPhoneHistory> findByEcUserIdIn(List<Integer> ecUserIds, Pageable pageable);

    /**
     * 统计某段时间、某人总的打电话次数
     */
    @Query("select count(id) , sum(callTime) , count(distinct callToNo) " +
            " from PtPhoneHistory where ecUserId = ?1 and startTime between ?2 and ?3")
    List<Object[]> findCount(Long ecUserId, Date begin, Date end);

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
    List<PtPhoneHistory> findByEcUserIdAndRealFalseAndCallTimeBetween(Long ecUserId, Date begin, Date end);
}
