package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.event.CompanyAddEvent;
import com.mindata.ecserver.main.event.OrderChangeEvent;
import com.mindata.ecserver.main.manager.PtOrderManager;
import com.mindata.ecserver.main.manager.PtProductManager;
import com.mindata.ecserver.main.model.secondary.PtOrder;
import com.mindata.ecserver.main.requestbody.CompanyBody;
import com.mindata.ecserver.main.requestbody.OrderBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.OrderVO;
import com.xiaoleilu.hutool.util.BeanUtil;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/1/23
 */
@Service
public class OrderService extends BaseService {
    @Resource
    private PtOrderManager ptOrderManager;
    @Resource
    private PtProductManager ptProductManager;

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
    public PtOrder add(Long companyId, Integer money, Long productId, String effectiveDate, String expiryDate, String
            memo) {
        PtOrder ptOrder = ptOrderManager.add(companyId, money, productId, effectiveDate, expiryDate, memo);
        eventPublisher.publishEvent(new OrderChangeEvent(companyId));
        return ptOrder;
    }

    public PtOrder add(OrderBody orderBody) {
        return add(orderBody.getCompanyId(), orderBody.getMoney(), orderBody.getProductId(), orderBody
                .getEffectiveDate(), orderBody.getExpiryDate(), orderBody.getMemo());
    }

    @Transactional(rollbackFor = Exception.class)
    public PtOrder update(OrderBody orderBody) {
        PtOrder old = ptOrderManager.findOne(orderBody.getId());
        BeanUtil.copyProperties(orderBody, old, BeanUtil.CopyOptions.create().setIgnoreNullValue(true));
        old = ptOrderManager.update(old);
        eventPublisher.publishEvent(new OrderChangeEvent(old.getCompanyId()));
        return old;
    }

    public List<OrderVO> findByCompanyId(Long companyId) {
        List<PtOrder> orders = ptOrderManager.findByCompanyId(companyId);
        List<OrderVO> vos = new ArrayList<>(orders.size());
        for (PtOrder ptOrder : orders) {
            OrderVO orderVO = new OrderVO();
            BeanUtil.copyProperties(ptOrder, orderVO);
            orderVO.setProductName(ptProductManager.findProductNameById(ptOrder.getProductId()));
            vos.add(orderVO);
        }
        return vos;
    }

    /**
     * 删除订单
     *
     * @param id
     *         id
     * @return 是否允许删除
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        PtOrder ptOrder = ptOrderManager.findOne(id);
        Integer count = ptOrderManager.countByCompanyId(ptOrder.getCompanyId());
        //只有一条order时，不让删除
        if (count <= 1) {
            return false;
        }

        ptOrderManager.delete(ptOrder);
        eventPublisher.publishEvent(new OrderChangeEvent(ptOrder.getCompanyId()));
        return true;
    }

    @EventListener
    public void companyAdd(CompanyAddEvent companyAddEvent) {
        CompanyBody companyBody = (CompanyBody) companyAddEvent.getSource();
        // 添加一个订单
        add(companyBody.getId(), companyBody.getMoney(), companyBody.getProductId(), companyBody
                .getEffectiveDate(), companyBody.getExpiryDate(), companyBody.getMemo());
    }
}
