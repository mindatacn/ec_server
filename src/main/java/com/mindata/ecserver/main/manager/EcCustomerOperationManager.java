package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.repository.primary.EcCustomerOperationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/12/8.
 */
@Service
public class EcCustomerOperationManager {
    @Resource
    private EcCustomerOperationRepository ecCustomerOperationRepository;

    /**
     * 查询某段时间内沟通过所有客户，按crmId进行distinct
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    public Long countDistinctCustomerBetween(Date begin, Date end) {
        return ecCustomerOperationRepository.countDistinctByCrmIdAndOperateTimeBetween(begin, end);
    }

    /**
     * 查询某段时间内沟通过所有客户，按crmId进行distinct，并且是市场部添加的
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    public Long countDistinctCustomerBetweenAndIsShiChang(Date begin, Date end) {
        return ecCustomerOperationRepository.countDistinctByCrmIdAndOperateTimeBetweenAndShiChang(begin, end);
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
    public Long countByOperateTypeAndTimeBetween(String type, Date begin, Date end) {
        return ecCustomerOperationRepository.countAddedAndOperateTimeBetween(type, begin, end);
    }

    /**
     * 查询某段时间内沟通过所有客户，并且状态为"新增客户"，并且是市场部添加的
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    public Long countByOperateTypeAndTimeBetweenAndIsShiChang(String type, Date begin, Date end) {
        return ecCustomerOperationRepository.countAddedAndOperateTimeBetweenAndIsShiChang(type, begin, end);
    }

    /**
     * 查询某段时间有意向
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 数量
     */
    public Long countByIntentedAndTimeBetween(Date begin, Date end) {
        return ecCustomerOperationRepository.countIntentedAndOperateTimeBetween(begin, end);
    }
}
