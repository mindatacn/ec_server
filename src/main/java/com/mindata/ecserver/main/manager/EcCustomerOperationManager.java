package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcCustomerOperation;
import com.mindata.ecserver.main.repository.primary.CustomerOperationRepository;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/27.
 */
@Service
public class EcCustomerOperationManager {
    @Resource
    private CustomerOperationRepository customerOperationRepository;

    /**
     * 查询某客户当前沟通状态
     *
     * @param crmId
     *         客户id
     * @return code
     */
    public Integer findCodeByCustomerId(Long crmId) {
        Pageable pageable = new PageRequest(0, 1);
        List<EcCustomerOperation> customerOperations = customerOperationRepository.findByCrmIdOrderByOperateTimeDesc
                (crmId, pageable);
        if (CollectionUtil.isEmpty(customerOperations)) {
            return 0;
        }
        return customerOperations.get(0).getStatusCode();
    }
}
