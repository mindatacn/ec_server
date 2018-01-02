package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.specify.Criteria;
import com.mindata.ecserver.global.specify.MySelector;
import com.mindata.ecserver.global.specify.Restrictions;
import com.mindata.ecserver.main.model.secondary.PtCustomerState;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/12/28.
 */
@Component
public class PtCustomerStateManager {
    @Resource(name = "entityManagerSecondary")
    private EntityManager entityManager;

    private Criteria<PtCustomerState> buildCriteria(Integer sourceFrom, Date begin, Date end) {
        Criteria<PtCustomerState> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("oldData", false, true));
        criteria.add(Restrictions.gt("operateTime", begin, true));
        criteria.add(Restrictions.lt("operateTime", end, true));
        if (sourceFrom != 0) {
            criteria.add(Restrictions.eq("sourceFrom", sourceFrom, true));
        }
        return criteria;
    }

    private MySelector buildSelections(String fieldName) {
        return Restrictions.countDistinct(fieldName);
    }

    private CriteriaQuery buildQuery(Criteria<PtCustomerState> criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery();
        Root<PtCustomerState> root = criteriaQuery.from(PtCustomerState.class);
        criteriaQuery.select(buildSelections("crmId").getSelection(root, builder));
        criteriaQuery.where(criteria.toPredicate(root, criteriaQuery, builder));
        return criteriaQuery;
    }

    /**
     * 查询某段时间内沟通过所有客户，按crmId进行distinct
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    public Integer countDistinctCustomerBetween(Integer sourceFrom, Date begin, Date end) {
        Criteria<PtCustomerState> criteria = buildCriteria(sourceFrom, begin, end);
        Object distinctCount = entityManager.createQuery(buildQuery(criteria)).getSingleResult();
        return CommonUtil.parseObject(distinctCount).intValue();
    }

    /**
     * 查询某段时间内沟通过所有客户，并且状态为"新增客户"
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    public Integer countByOperateTypeAndTimeBetween(String type, Integer sourceFrom, Date begin, Date end) {
        Criteria<PtCustomerState> criteria = buildCriteria(sourceFrom, begin, end);
        criteria.add(Restrictions.eq("operateType", type, true));
        Object distinctCount = entityManager.createQuery(buildQuery(criteria)).getSingleResult();
        return CommonUtil.parseObject(distinctCount).intValue();
    }

    /**
     * 查询某段时间有意向或者已成交
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    public Integer countByIntentedAndTimeBetween(Integer sourceFrom, Integer saleState, Date begin, Date end) {
        Criteria<PtCustomerState> criteria = buildCriteria(sourceFrom, begin, end);
        criteria.add(Restrictions.eq("saleState", saleState, true));
        Object distinctCount = entityManager.createQuery(buildQuery(criteria)).getSingleResult();
        return CommonUtil.parseObject(distinctCount).intValue();
    }

}
