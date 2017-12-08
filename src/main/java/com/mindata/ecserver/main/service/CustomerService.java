package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.manager.EcCustomerManager;
import com.mindata.ecserver.main.manager.EcCustomerOperationManager;
import com.mindata.ecserver.main.manager.PtPhoneHistoryManager;
import com.mindata.ecserver.main.repository.secondary.PtPushSuccessResultRepository;
import com.mindata.ecserver.main.vo.SaleStateVO;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/12/8.
 */
@Service
public class CustomerService {
    @Resource
    private EcCustomerOperationManager ecCustomerOperationManager;
    @Resource
    private EcCustomerManager ecCustomerManager;
    @Resource
    private PtPushSuccessResultRepository ptPushSuccessResultRepository;
    @Resource
    private PtPhoneHistoryManager ptPhoneHistoryManager;

    /**
     * 分析某段时间的已沟通的线索信息和销售信息
     *
     * @return 聚合数据
     */
    public SaleStateVO analyzeSaleState(String begin, String end) {
        Date beginTime = CommonUtil.beginOfDay(begin);
        Date endTime = CommonUtil.endOfDay(end);

        SaleStateVO saleStateVO = new SaleStateVO();
        //时间段总的沟通客户数
        Long totalContact = ecCustomerOperationManager.countDistinctCustomerBetween(beginTime, endTime);
        //由技术提供的
        Long maidaTotalContact = ptPushSuccessResultRepository.countByCrmIdInList(beginTime, endTime);
        Long shichangTotalContact = ecCustomerOperationManager.countDistinctCustomerBetweenAndIsShiChang(beginTime,
                endTime);
        saleStateVO.setTotalContact(Arrays.asList(maidaTotalContact, totalContact - maidaTotalContact -
                shichangTotalContact, shichangTotalContact, totalContact));

        //线索新增量
        Long addedTotalCount = ecCustomerOperationManager.countByOperateTypeAndTimeBetween("新增客户", beginTime, endTime);
        Long mdAddedTotalCount = ptPushSuccessResultRepository.countByCrmIdInListAndType("新增客户", beginTime,
                endTime);
        Long shichangAddedTotalCount = ecCustomerOperationManager.countByOperateTypeAndTimeBetweenAndIsShiChang
                ("新增客户", beginTime,
                endTime);
        saleStateVO.setAddedContact(Arrays.asList(mdAddedTotalCount, addedTotalCount - mdAddedTotalCount -
                shichangAddedTotalCount, shichangAddedTotalCount, addedTotalCount));

        //有意向线索量，customer里status_code为2，3，4
        Long intentTotalCount = ecCustomerManager.findTotalIntentCount("2, 3, 4", beginTime, endTime);
        Long mdIntentTotalCount = ptPushSuccessResultRepository.countByCrmIdInListAndIsIntent("2, 3, 4", beginTime,
                endTime);
        saleStateVO.setIntentedContact(Arrays.asList(mdIntentTotalCount, intentTotalCount - mdIntentTotalCount, 0L,
                intentTotalCount));

        //成交线索量，customer里status_code为5
        Long saledTotalCount = ecCustomerManager.findTotalSaledCount("5", beginTime, endTime);
        Long mdSaledTotalCount = ptPushSuccessResultRepository.countByCrmIdInListAndIsIntent("5", beginTime,
                endTime);
        saleStateVO.setSaledContact(Arrays.asList(mdSaledTotalCount, saledTotalCount - mdSaledTotalCount, 0L,
                saledTotalCount));

        //接通量
        Long connectedCount = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(0, beginTime, endTime);
        Long mdConnectedCount = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(0,
                beginTime, endTime);
        saleStateVO.setConnectedContact(Arrays.asList(mdConnectedCount, connectedCount - mdConnectedCount, 0L,
                connectedCount));


        //有效沟通量
        Long validCount = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(30, beginTime, endTime);
        Long mdValidCount = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(30, beginTime,
                endTime);
        saleStateVO.setValidedContact(Arrays.asList(mdValidCount, validCount - mdValidCount, 0L, validCount));
        return saleStateVO;
    }
}
