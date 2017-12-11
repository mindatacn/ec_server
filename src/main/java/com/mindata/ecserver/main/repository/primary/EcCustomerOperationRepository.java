package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcCustomerOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcCustomerOperationRepository extends JpaRepository<EcCustomerOperation, Long> {
    /**
     * 查询某段时间总的不重复的客户数量
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束
     * @return 总量
     */
    @Query(value = "SELECT count(DISTINCT crm_id) FROM company.ec_customer_operation WHERE operate_time BETWEEN ?1 " +
            "AND ?2 AND crm_id " +
            "NOT " +
            "IN ( SELECT crm_id FROM company.ec_bjmd_olddata)", nativeQuery = true)
    Long countDistinctByCrmIdAndOperateTimeBetween(Date begin, Date end);

    @Query(value = "SELECT count(DISTINCT crm_id) FROM company.ec_customer_operation WHERE operate_time BETWEEN ?1 " +
            "AND ?2 AND" +
            " (content " +
            "LIKE '%百度技术%' OR content LIKE '%网站咨询%' OR content LIKE '%400%') AND crm_id NOT IN ( SELECT crm_id FROM " +
            "company.ec_bjmd_olddata" +
            ")", nativeQuery = true)
    Long countDistinctByCrmIdAndOperateTimeBetweenAndShiChang(Date begin, Date end);

    /**
     * 查询某段时间沟通的用户中，是新增的客户
     *
     * @param operateType
     *         类型
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 结果
     */
    @Query(value = "SELECT count(DISTINCT crm_id) FROM company.ec_customer_operation WHERE operate_type = ?1 AND " +
            "crm_id NOT " +
            "IN ( SELECT " +
            "crm_id FROM company.ec_bjmd_olddata) AND operate_time BETWEEN ?2 " +
            "AND ?3 ", nativeQuery = true)
    Long countAddedAndOperateTimeBetween(String operateType, Date begin, Date end);

    /**
     * 查询某段时间沟通的用户中，是新增的客户，且是市场部添加的
     *
     * @param operateType
     *         类型
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 结果
     */
    @Query(value = "select count(DISTINCT crm_id) from company.ec_customer_operation where operate_type = ?1 AND " +
            "crm_id NOT " +
            "IN ( SELECT crm_id FROM company.ec_bjmd_olddata) " +
            "and " +
            "(content " +
            "like '%百度技术%' " +
            "or content " +
            "like '%网站咨询%' or content like '%400%') and operate_time " +
            "between " +
            "?2 " +
            "and ?3", nativeQuery = true)
    Long countAddedAndOperateTimeBetweenAndIsShiChang(String operateType, Date begin, Date end);

    @Query(value = "SELECT count(DISTINCT crm_id) FROM company.ec_customer_operation WHERE 1 = 1 AND crm_id NOT IN ( " +
            "SELECT crm_id FROM company.ec_bjmd_olddata) AND " +
            "(content LIKE " +
            "'%初步意向%' OR content " +
            "LIKE '%意向客户%') AND operate_time " +
            "BETWEEN " +
            "?1 " +
            "AND ?2", nativeQuery = true)
    Long countIntentedAndOperateTimeBetween(Date begin, Date end);
}
