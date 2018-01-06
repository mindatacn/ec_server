package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.repository.secondary.PtCustomerStateRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/12/28.
 */
@Component
public class PtCustomerStateManager {
    @Resource
    private PtCustomerStateRepository ptCustomerStateRepository;

    private Integer count(Integer sourceFrom, Integer saleState, String operateType, Date begin, Date end) {
        List<Integer> sourceFromList, saleStateList;
        if (sourceFrom == null || sourceFrom == 0) {
            sourceFromList = Arrays.asList(1, 2, 3);
        } else {
            sourceFromList = Arrays.asList(sourceFrom);
        }
        if (saleState == null) {
            saleStateList = Arrays.asList(0, 1, 2);
        } else {
            saleStateList = Arrays.asList(saleState);
        }
        if (operateType != null) {
            return ptCustomerStateRepository.countDistinctByOperateType(begin, end, sourceFromList, operateType);
        } else {
            return ptCustomerStateRepository.countDistinctAndOperateTimeBetween(begin, end, sourceFromList,
                    saleStateList);
        }
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
        return count(sourceFrom, null, null, begin, end);
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
        return count(sourceFrom, null, type, begin, end);
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
        return count(sourceFrom, saleState, null, begin, end);
    }

}
