package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPhoneHistoryCompanyRepository extends JpaRepository<PtPhoneHistoryCompany, Integer> {
    /**
     * 查询某个公司一段时间内的通话历史统计
     *
     * @param companyId
     *         companyId
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @param pageable
     *         分页
     * @return page
     */
    Page<PtPhoneHistoryCompany> findByCompanyIdAndStartTimeBetween(Long companyId, Date begin, Date end, Pageable
            pageable);

    /**
     * 统计某段时间公司的统计数量
     *
     * @param companyId
     *         公司id
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    Integer countByCompanyIdAndStartTimeBetween(Long companyId, Date begin, Date end);

    /**
     * 查询某公司通话历史
     *
     * @param companyId
     *         companyId
     * @return 结果集
     */
    List<PtPhoneHistoryCompany> findByCompanyId(Long companyId);

    /**
     * 统计某公司某段时间的统计聚合数据
     *
     * @param companyIds
     *         公司id
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 聚合结果
     */
    @Query("select sum(totalCallTime), sum(totalCallCount), sum(totalCustomer), sum(pushCount), sum(validCount), sum" +
            "(noPushCount), sum(pushCallTime), sum(pushCustomer), sum(pushValidCount)" +
            " " +
            " from PtPhoneHistoryCompany where companyId in ?1 and startTime between ?2 and ?3")
    List<Object[]> findCount(List<Long> companyIds, Date begin, Date end);
}
