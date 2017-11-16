package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtCustomerTagGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2017/11/15
 */
public interface PtCustomerTagGroupRepository extends JpaRepository<PtCustomerTagGroup, Long> {

    /**
     * 根据groupId查询
     *
     * @param groupId
     *         groupId
     * @return group
     */
    PtCustomerTagGroup findByGroupId(Long groupId);
}
