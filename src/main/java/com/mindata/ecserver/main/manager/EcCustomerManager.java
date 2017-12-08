package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcCustomer;
import com.mindata.ecserver.main.repository.primary.EcCustomerRepository;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/27.
 */
@Service
public class EcCustomerManager {
    @Resource
    private EcCustomerRepository ecCustomerRepository;

    /**
     * 查询某客户当前沟通状态
     *
     * @param crmId
     *         客户id
     * @return code
     */
    public Integer findCodeByCustomerId(Long crmId) {
        Pageable pageable = new PageRequest(0, 1);
        List<EcCustomer> customerOperations = ecCustomerRepository.findByCrmIdOrderByUpdateTimeDesc
                (crmId, pageable);
        if (CollectionUtil.isEmpty(customerOperations)) {
            return 0;
        }
        return customerOperations.get(0).getStatusCode();
    }

    /**
     * 查询某段时间总的有意向的数量
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 结果
     */
    public Long findTotalIntentCount(String statusCodes, Date begin, Date end) {
        return ecCustomerRepository.countByCrmIdInListAndIntented(statusCodes, begin, end);
    }

    /**
     * 查询某段时间总的成交的数量
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 结果
     */
    public Long findTotalSaledCount(String statusCodes, Date begin, Date end) {
        return ecCustomerRepository.countByCrmIdInListAndSaled(statusCodes, begin, end);
    }
}
