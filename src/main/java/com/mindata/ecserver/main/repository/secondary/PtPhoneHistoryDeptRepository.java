package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryDept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPhoneHistoryDeptRepository extends JpaRepository<PtPhoneHistoryDept, Long> {
    /**
     * 查询某个部门一段时间内的通话历史统计
     *
     * @param deptId
     *         deptId
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @param pageable
     *         分页
     * @return page
     */
    Page<PtPhoneHistoryDept> findByDeptIdAndStartTimeBetween(Long deptId, Date begin, Date end, Pageable pageable);

    /**
     * 统计某段时间、多个部门总的打电话次数
     */
    @Query("select sum(totalCallTime), sum(totalCallCount), sum(totalCustomer), sum(pushCount), sum(validCount), sum" +
            "(noPushCount), sum(pushCallTime), sum(pushCustomer), sum(pushValidCount)" +
            " " +
            " from PtPhoneHistoryDept where deptId in ?1 and startTime between ?2 and ?3")
    List<Object[]> findCount(List<Long> deptIds, Date begin, Date end);


    /**
     * 获取每天每个部门的统计数量，正常返回1，如果没值返回0
     *
     * @param ecDeptId
     *         部门id
     * @param begin
     *         每天开始
     * @param end
     *         每天结束
     * @return 1或者0
     */
    Integer countByDeptIdAndStartTimeBetween(Long ecDeptId, Date begin, Date end);
}
