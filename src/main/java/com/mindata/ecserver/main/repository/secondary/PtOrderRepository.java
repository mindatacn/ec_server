package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hanliqiang wrote on 2018/1/22
 */
public interface PtOrderRepository extends JpaRepository<PtOrder, Long> {
    /**
     * 根据公司ID 查找当前公司最新的一条消息
     *
     * @param companyId companyId
     * @return Long
     */
    PtOrder findFirstByCompanyIdOrderByExpiryDateDesc(Long companyId);

    /**
     * 某公司的订单数量
     *
     * @param companyId
     *         companyId
     * @return 数量
     */
    Integer countByCompanyId(Long companyId);

    /**
     * 查询某个公司的订单
     *
     * @param companyId
     *         companyId
     * @return 集合
     */
    List<PtOrder> findByCompanyId(Long companyId);
}
