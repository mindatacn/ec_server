package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPushSuccessResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPushSuccessResultRepository extends JpaRepository<PtPushSuccessResult, Long>,
        JpaSpecificationExecutor<PtPushSuccessResult> {
    /**
     * 统计已推送的数量
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 数量
     */
    int countByCreateTimeBetween(Date begin, Date end);

    /**
     * 查询某段时间内，由平台推送的客户沟通总数量
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 结果
     */
    @Query(value = "SELECT count(id) FROM ec_server.`pt_push_success_result` WHERE crm_id IN(SELECT crm_id FROM " +
            "company.`ec_customer_operation` WHERE `operate_time` BETWEEN ?1 AND ?2 " +
            "GROUP BY crm_id)", nativeQuery = true)
    Long countByCrmIdInList(Date begin, Date end);

    /**
     * 查询某段时间内，由平台推送的客户沟通总数量
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 结果
     */
    @Query(value = "SELECT count(id) FROM ec_server.`pt_push_success_result` WHERE crm_id IN(SELECT crm_id FROM " +
            "company.`ec_customer_operation` WHERE `operate_type` = ?1 AND  `operate_time` BETWEEN ?2 AND ?3 " +
            "GROUP BY crm_id)", nativeQuery = true)
    Long countByCrmIdInListAndType(String type, Date begin, Date end);

    /**
     * 查询某段时间有意向的客户总数，且客户是我们推送过去的
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    @Query(value = "SELECT count(DISTINCT id) FROM ec_server.`pt_push_success_result` WHERE crm_id IN (" +
            "SELECT crm_id FROM company.`ec_customer` WHERE status_code IN (?1) AND crm_id IN(SELECT" +
            " " +
            "crm_id FROM" +
            " " +
            "company.`ec_customer_operation` WHERE `operate_time` BETWEEN ?2 AND ?3 AND operate_type = '更新客户阶段'" +
            "AND content LIKE '%合作成交'" +
            "GROUP BY crm_id))", nativeQuery = true)
    Long countByCrmIdInListAndIsIntent(String statusCodes, Date begin, Date end);

    /**
     * 查询某段时间通话大于多少秒的总数量，且是我们推送的
     *
     * @param seconds
     *         秒数
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 总数量
     */
    @Query(value = "SELECT count(DISTINCT id) FROM pt_push_success_result WHERE crm_id IN " +
            "(SELECT crm_id FROM pt_phone_history WHERE call_time > ?1 AND start_time BETWEEN ?2 AND ?3)", nativeQuery =
            true)
    Long countCallTimeGreaterThanAndStartTimeBetween(Integer seconds, Date begin, Date end);

    /**
     * 根据crmId查询结果
     *
     * @param crmId
     *         crmId
     * @return 结果
     */
    List<PtPushSuccessResult> findByCrmId(Long crmId);

    /**
     * 理论上不能有多个，但是有测试数据需要删除掉
     *
     * @param contactId
     *         EcContactEntity表的id
     * @return 结果集
     */
    List<PtPushSuccessResult> findByContactId(Long contactId);

    /**
     * 根据mobile查询crmId
     *
     * @param mobile
     *         mobile
     * @return 记录
     */
    List<PtPushSuccessResult> findByMobile(String mobile);
}
