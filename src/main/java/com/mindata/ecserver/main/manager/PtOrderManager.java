package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtOrder;
import com.mindata.ecserver.main.repository.secondary.PtOrderRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public PtOrder add(Long companyId, Integer money, Long productId, String effectiveDate, String expiryDate, String
            memo) {
        PtOrder order = new PtOrder();
        order.setCompanyId(companyId);
        order.setMoney(money);
        order.setProductId(productId);
        order.setEffectiveDate(CommonUtil.beginOfDay(effectiveDate));
        order.setExpiryDate(CommonUtil.endOfDay(expiryDate));
        order.setCreateTime(CommonUtil.getNow());
        order.setUpdateTime(CommonUtil.getNow());
        order.setMemo(memo);
        return ptOrderRepository.save(order);
    }

    public PtOrder add(PtOrder ptOrder) {
        return ptOrderRepository.save(ptOrder);
    }

    /**
     * 修改订单
     * @param ptOrder ptOrder
     */
    public PtOrder update(PtOrder ptOrder) {
        ptOrder.setUpdateTime(CommonUtil.getNow());
        return ptOrderRepository.save(ptOrder);
    }

    public PtOrder findOne(Long id) {
        return ptOrderRepository.findOne(id);
    }

    public void delete(PtOrder ptOrder) {
        if (ptOrder != null) {
            ptOrderRepository.delete(ptOrder);
        }
    }

    /**
     * 根据公司id查找最新的订单
     *
     * @param companyId
     *         companyId
     * @return PtOrder
     */
    public PtOrder findNewOrderByCompanyId(Long companyId) {
        return ptOrderRepository.findByCompanyIdOrderByExpiryDateDesc(companyId);
    }

    /**
     * 判断公司订单是否已经过期
     *
     * @param companyId
     *         id
     * @return 是
     */
    public boolean isExpire(Long companyId) {
        PtOrder ptOrder = findNewOrderByCompanyId(companyId);
        return CommonUtil.getNow().after(ptOrder.getExpiryDate());
    }

    public Integer countByCompanyId(Long companyId) {
        Integer count = ptOrderRepository.countByCompanyId(companyId);
        return count == null ? 0 : count;
    }

}
