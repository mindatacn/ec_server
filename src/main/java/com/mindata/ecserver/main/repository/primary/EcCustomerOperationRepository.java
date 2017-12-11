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
    @Query("select count(DISTINCT crmId) from EcCustomerOperation where operateTime between ?1 and ?2")
    Long countDistinctByCrmIdAndOperateTimeBetween(Date begin, Date end);

    @Query("select count(DISTINCT crmId) from EcCustomerOperation where operateTime between ?1 and ?2 and (content " +
            "like '%百度技术%' or content like '%网站咨询%' or content like '%400%')")
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
    @Query("select count(DISTINCT crmId) from EcCustomerOperation where operateType = ?1 and operateTime between ?2 " +
            "and ?3")
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
    @Query("select count(DISTINCT crmId) from EcCustomerOperation where operateType = ?1 and (content like '%百度技术%' " +
            "or content " +
            "like '%网站咨询%' or content like '%400%') and operateTime " +
            "between " +
            "?2 " +
            "and ?3 ")
    Long countAddedAndOperateTimeBetweenAndIsShiChang(String operateType, Date begin, Date end);

    @Query("select count(DISTINCT crmId) from EcCustomerOperation where 1 = 1 and (content like '%初步意向%' or content " +
            "like '%意向客户%') and operateTime " +
            "between " +
            "?1 " +
            "and ?2 ")
    Long countIntentedAndOperateTimeBetween(Date begin, Date end);
}
