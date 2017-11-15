package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtCustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
public interface PtCustomerGroupRepository extends JpaRepository<PtCustomerGroup, Long> {
    /**
     * 根据groupId查找对象
     * @param groupId 分组Id
     * @return 结果
     */
    PtCustomerGroup findByGroupId(String groupId);
}
