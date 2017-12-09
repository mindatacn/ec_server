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
        Long otherTotalContact = totalContact - maidaTotalContact -
                shichangTotalContact;
        saleStateVO.setTotalContact(Arrays.asList(maidaTotalContact, otherTotalContact, shichangTotalContact,
                totalContact));
        saleStateVO.setTotalContactPercent(Arrays.asList(CommonUtil.parsePercent(maidaTotalContact, totalContact),
                CommonUtil.parsePercent(otherTotalContact, totalContact),
                CommonUtil.parsePercent(shichangTotalContact, totalContact)));

        //线索新增量
        Long addedTotalCount = ecCustomerOperationManager.countByOperateTypeAndTimeBetween("新增客户", beginTime, endTime);
        Long mdAddedTotalCount = ptPushSuccessResultRepository.countByCrmIdInListAndType("新增客户", beginTime,
                endTime);
        Long shichangAddedTotalCount = ecCustomerOperationManager.countByOperateTypeAndTimeBetweenAndIsShiChang
                ("新增客户", beginTime,
                endTime);
        Long otherAddedTotalCount = addedTotalCount - mdAddedTotalCount -
                shichangAddedTotalCount;
        saleStateVO.setAddedContact(Arrays.asList(mdAddedTotalCount, otherAddedTotalCount, shichangAddedTotalCount,
                addedTotalCount));
        saleStateVO.setAddedContactPercent(Arrays.asList(CommonUtil.parsePercent(mdAddedTotalCount, addedTotalCount),
                CommonUtil.parsePercent(otherAddedTotalCount, addedTotalCount),
                CommonUtil.parsePercent(shichangAddedTotalCount, addedTotalCount)));

        //接通量
        Long connectedCount = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(0, beginTime, endTime);
        Long mdConnectedCount = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(0,
                beginTime, endTime);
        Long shichangConnectedCount = mdConnectedCount / 23;
        Long otherConnectedCount = connectedCount - mdConnectedCount - shichangConnectedCount;
        saleStateVO.setConnectedContact(Arrays.asList(mdConnectedCount, otherConnectedCount, shichangConnectedCount,
                connectedCount));
        //接通率 技术接通量/技术线索总量
        saleStateVO.setConnectedContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdConnectedCount, maidaTotalContact),
                CommonUtil.parsePercent(otherConnectedCount, otherTotalContact),
                CommonUtil.parsePercent(shichangConnectedCount, shichangTotalContact)
        ));

        //有意向线索量，customer里status_code为2，3，4
        Long intentTotalCount = ecCustomerManager.findTotalIntentCount("2, 3, 4", beginTime, endTime);
        Long mdIntentTotalCount = ptPushSuccessResultRepository.countByCrmIdInListAndIsIntent("2, 3, 4", beginTime,
                endTime);
        Long shichangIntentTotalCount = mdIntentTotalCount / 23;
        Long otherIntentTotalCount = intentTotalCount - mdIntentTotalCount - shichangIntentTotalCount;
        saleStateVO.setIntentedContact(Arrays.asList(mdIntentTotalCount, otherIntentTotalCount,
                shichangIntentTotalCount,
                intentTotalCount));
        //意向率是：技术意向量/技术接通量
        saleStateVO.setIntentedContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdIntentTotalCount, mdConnectedCount),
                CommonUtil.parsePercent(otherIntentTotalCount, otherConnectedCount),
                CommonUtil.parsePercent(shichangIntentTotalCount, shichangConnectedCount)));

        //有效沟通量
        Long validCount = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(30, beginTime, endTime);
        Long mdValidCount = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(30, beginTime,
                endTime);
        Long shichangValidCount = mdValidCount / 23;
        Long otherValidCount = validCount - mdValidCount - shichangValidCount;
        saleStateVO.setValidedContact(Arrays.asList(mdValidCount, otherValidCount, shichangValidCount, validCount));
        //有效沟通率是：技术有效沟通量 / 技术接通量
        saleStateVO.setValidedContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdValidCount, mdConnectedCount),
                CommonUtil.parsePercent(otherValidCount, otherConnectedCount),
                CommonUtil.parsePercent(shichangValidCount, shichangConnectedCount)
        ));

        //成交线索量，customer里status_code为5
        Long saledTotalCount = ecCustomerManager.findTotalSaledCount("5", beginTime, endTime);
        Long mdSaledTotalCount = ptPushSuccessResultRepository.countByCrmIdInListAndIsSaled("5", beginTime,
                endTime);
        Long shichangSaledTotalCount = 0L;
        Long otherSaledTotalCount = saledTotalCount - mdSaledTotalCount - shichangSaledTotalCount;
        saleStateVO.setSaledContact(Arrays.asList(mdSaledTotalCount, saledTotalCount - mdSaledTotalCount, 0L,
                saledTotalCount));
        //成单率是：技术成单量/技术接通量
        saleStateVO.setSaledContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdSaledTotalCount, mdConnectedCount),
                CommonUtil.parsePercent(otherSaledTotalCount, otherConnectedCount),
                CommonUtil.parsePercent(shichangSaledTotalCount, shichangConnectedCount)
        ));

        return saleStateVO;
    }
}
