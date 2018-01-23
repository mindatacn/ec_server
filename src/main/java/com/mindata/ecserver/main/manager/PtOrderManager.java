package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtOrder;
import com.mindata.ecserver.main.repository.secondary.PtOrderRepository;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hanliqiang wrote on 2018/1/22
 */
@Service
public class PtOrderManager {
    @Resource
    private PtOrderRepository ptOrderRepository;



    /**
     * 新增一个订单
     */
    public void add(Long companyId, BigDecimal money, Long productId, Date effectiveDate, Date expiryDate,String memo) {
        PtOrder order = new PtOrder();
        order.setCompanyId(companyId);
        order.setMoney(money);
        order.setProductId(productId);
        order.setEffectiveDate(effectiveDate);
        order.setExpiryDate(DateUtil.endOfDay(expiryDate));
        order.setCreateTime(CommonUtil.getNow());
        order.setMemo(memo);
        ptOrderRepository.save(order);
    }
    public void update(PtOrder ptOrder){
        ptOrderRepository.save(ptOrder);
    }

    /**
     * 根据公司id查找最新的订单
     * @param companyId companyId
     * @return PtOrder
     */
    public PtOrder findByCompanyIdAndProductId(Long companyId,Long productId){
        Long orderId = ptOrderRepository.findPtOrderByMaxIdAndProductId(companyId,productId);
        return ptOrderRepository.findOne(orderId);
    }

    /**
     * 根据公司id和商品id统计有多少个订单
     * @param companyId companyId
     * @param productId productId
     * @return Integer
     */
    public Integer countByCompanyIdAndProductId(Long companyId,Long productId){
        return ptOrderRepository.countByCompanyIdAndProductId(companyId,productId);
    }

    /**
     * 查找当前公司最新的一条数据
     * @param companyId companyId
     * @return List
     */
    public PtOrder findByCompanyId(Long companyId){
        Long orderId = ptOrderRepository.findByCompanyId(companyId);
        return ptOrderRepository.findOne(orderId);
    }

}
