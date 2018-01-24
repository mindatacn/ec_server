package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.event.CompanyAddEvent;
import com.mindata.ecserver.main.manager.PtCompanyManager;
import com.mindata.ecserver.main.manager.PtOrderManager;
import com.mindata.ecserver.main.model.secondary.PtCompany;
import com.mindata.ecserver.main.requestbody.CompanyBody;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hanliqiang wrote on 2018/1/23
 */
@Service
public class OrderService {
    @Resource
    private PtOrderManager ptOrderManager;
    @Resource
    private PtCompanyManager ptCompanyManager;

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
    public void add(Long companyId, BigDecimal money, Long productId, Date effectiveDate, Date expiryDate, String
            memo) {
        ptOrderManager.add(companyId, money, productId, effectiveDate, expiryDate, memo);
        Integer count = ptOrderManager.countByCompanyIdAndProductId(companyId, productId);
        if (count > 1) {
            PtCompany ptCompany = ptCompanyManager.findOne(companyId);
            // 已续费
            ptCompany.setBuyStatus(2);
            ptCompanyManager.update(ptCompany);
        }
    }

    @EventListener
    public void companyAdd(CompanyAddEvent companyAddEvent) {
        CompanyBody companyBody = (CompanyBody) companyAddEvent.getSource();
        // 添加一个订单
        ptOrderManager.add(companyBody.getId(), companyBody.getMoney(), companyBody.getProductId(), companyBody
                .getEffectiveDate(), companyBody.getExpiryDate(), companyBody.getMemo());
    }
}
