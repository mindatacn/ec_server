package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.event.CompanyAddEvent;
import com.mindata.ecserver.main.event.OrderChangeEvent;
import com.mindata.ecserver.main.manager.PtOrderManager;
import com.mindata.ecserver.main.model.secondary.PtOrder;
import com.mindata.ecserver.main.requestbody.CompanyBody;
import com.mindata.ecserver.main.service.base.BaseService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2018/1/23
 */
@Service
public class OrderService extends BaseService {
    @Resource
    private PtOrderManager ptOrderManager;

    /**
     * 新增一条记录同时更改购买状态
     *
     * @param companyId
     *         companyId
     * @param money
     *         money
     * @param productId
     *         productId
     * @param effectiveDate
     *         effectiveDate
     * @param expiryDate
     *         expiryDate
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Long companyId, BigDecimal money, Long productId, Date effectiveDate, Date expiryDate, String
            memo) {
        ptOrderManager.add(companyId, money, productId, effectiveDate, expiryDate, memo);
        eventPublisher.publishEvent(new OrderChangeEvent(companyId));
    }

    @Transactional(rollbackFor = Exception.class)
    public PtOrder update(PtOrder ptOrder) {
        ptOrder = ptOrderManager.update(ptOrder);
        eventPublisher.publishEvent(new OrderChangeEvent(ptOrder.getCompanyId()));
        return ptOrder;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        PtOrder ptOrder = ptOrderManager.findOne(id);
        ptOrderManager.delete(ptOrder);
        eventPublisher.publishEvent(new OrderChangeEvent(ptOrder.getCompanyId()));
    }

    @EventListener
    public void companyAdd(CompanyAddEvent companyAddEvent) {
        CompanyBody companyBody = (CompanyBody) companyAddEvent.getSource();
        // 添加一个订单
        add(companyBody.getId(), companyBody.getMoney(), companyBody.getProductId(), companyBody
                .getEffectiveDate(), companyBody.getExpiryDate(), companyBody.getMemo());
    }
}
