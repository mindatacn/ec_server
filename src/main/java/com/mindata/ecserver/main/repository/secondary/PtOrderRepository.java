package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
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
    PtOrder findByCompanyIdOrderByExpiryDateDesc(Long companyId);

    /**
     *
     * @param companyId
     * @param productId
     * @return
     */
    Integer countByCompanyIdAndProductId(Long companyId, Long productId);

    /**
     *
     * @param nowTime
     * @return
     */
    List<PtOrder> findByExpiryDateLessThan(Date nowTime);
}
