package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
     * @param productId productId
     * @return Long
     */
    @Query(value = "select max(id) from PtOrder where companyId = ?1 and productId=?2")
    Long findPtOrderByMaxIdAndProductId(Long companyId, Long productId);

    /**
     * 根据公司id和商品id统计有多少数据
     * @param companyId
     * @param productId
     * @return
     */
    Integer countByCompanyIdAndProductId(Long companyId, Long productId);

    /**
     * 根据id查找订单
     * @param companyId companyId
     * @return List
     */
    @Query(value = "select max(id) from PtOrder where companyId = ?1")
    Long findByCompanyId(Long companyId);
}
