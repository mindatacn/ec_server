package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcCustomer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcCustomerRepository extends JpaRepository<EcCustomer, Long> {
    /**
     * 查找某个客户最新的状态
     *
     * @param crmId
     *         客户id
     * @param pageable
     *         分页
     * @return 客户状态
     */
    List<EcCustomer> findByCrmIdOrderByUpdateTimeDesc(Long crmId, Pageable pageable);

    /**
     * 查询某段时间成交的客户总数
     *
     * @param statusCodes
     *         如2,3,4
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    @Query(value = "SELECT count(DISTINCT crm_id) FROM company.`ec_customer` WHERE status_code IN (?1) AND crm_id IN" +
            "(SELECT" +
            " " +
            "crm_id FROM" +
            " " +
            "company.`ec_customer_operation` WHERE `operate_time` BETWEEN ?2 AND ?3 AND operate_type = '更新客户阶段' " +
            "AND content LIKE '%合作成交'" +
            " GROUP BY crm_id)", nativeQuery = true)
    Long countByCrmIdInListAndSaled(String statusCodes, Date begin, Date end);

    /**
     * 查询某段时间有意向的客户总数
     *
     * @param statusCodes
     *         2,3,4
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    @Query(value = "SELECT count(DISTINCT crm_id) FROM company.`ec_customer` WHERE status_code IN (?1) AND crm_id IN" +
            "(SELECT" +
            " " +
            "crm_id FROM" +
            " " +
            "company.`ec_customer_operation` WHERE `operate_time` BETWEEN ?2 AND ?3 " +
            "GROUP BY crm_id)", nativeQuery = true)
    Long countByCrmIdInListAndIntented(String statusCodes, Date begin, Date end);
}
