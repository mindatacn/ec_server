package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtCustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
public interface PtCustomerGroupRepository extends JpaRepository<PtCustomerGroup, Long> {
    /**
     * 根据groupId查找对象
     * @param groupId 分组Id
     * @return 结果
     */
    PtCustomerGroup findByGroupId(Long groupId);

    /**
     * 查询某个用户的group集合
     *
     * @param userId
     *         用户id
     * @return group集合
     */
    List<PtCustomerGroup> findByUserId(Long userId);
}
