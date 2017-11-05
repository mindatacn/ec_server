package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

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
    Page<PtPhoneHistoryCompany> findByCompanyIdAndStartTimeBetween(Integer companyId, Date begin, Date end, Pageable
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
    Integer countByCompanyIdAndStartTimeBetween(Integer companyId, Date begin, Date end);
}
