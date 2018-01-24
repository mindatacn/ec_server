package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtOrder;
import com.mindata.ecserver.main.repository.secondary.PtOrderRepository;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
    public PtOrder add(Long companyId, Integer money, Long productId, Date effectiveDate, Date expiryDate, String
            memo) {
        PtOrder order = new PtOrder();
        order.setCompanyId(companyId);
        order.setMoney(money);
        order.setProductId(productId);
        order.setEffectiveDate(effectiveDate);
        order.setExpiryDate(DateUtil.endOfDay(expiryDate));
        order.setCreateTime(CommonUtil.getNow());
        order.setUpdateTime(CommonUtil.getNow());
        order.setMemo(memo);
        return ptOrderRepository.save(order);
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
