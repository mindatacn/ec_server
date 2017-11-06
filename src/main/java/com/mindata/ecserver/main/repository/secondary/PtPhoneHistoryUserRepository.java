package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPhoneHistoryUserRepository extends JpaRepository<PtPhoneHistoryUser, Integer> {
    /**
     * 查询某个用户一段时间内的通话历史统计
     *
     * @param userId
     *         userId
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @param pageable
     *         分页
     * @return page
     */
    Page<PtPhoneHistoryUser> findByUserIdAndStartTimeBetween(Integer userId, Date begin, Date end, Pageable pageable);

    List<PtPhoneHistoryUser> findByUserIdAndStartTimeBetween(Integer userId, Date begin, Date end);

    /**
     * 统计某天、某个部门所有员工总的打电话次数
     */
    @Query("select sum(totalCallTime), sum(totalCallCount), sum(totalCustomer), sum(pushCount), sum(validCount) " +
            " from PtPhoneHistoryUser where userId in ?1 and startTime between ?2 and ?3")
    List<Object[]> findCount(List<Integer> userIds, Date begin, Date end);

    @Query("select sum(totalCallTime), sum(totalCallCount), sum(totalCustomer), sum(pushCount), sum(validCount) " +
            " from PtPhoneHistoryUser where userId = ?1 and startTime between ?2 and ?3")
    List<Object[]> findCount(Integer userId, Date begin, Date end);
}
