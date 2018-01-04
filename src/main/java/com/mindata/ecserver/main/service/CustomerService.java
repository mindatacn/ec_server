package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.cache.SaleStateCache;
import com.mindata.ecserver.main.manager.PtCustomerStateManager;
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
    private PtCustomerStateManager ptCustomerStateManager;
    @Resource
    private PtPushSuccessResultRepository ptPushSuccessResultRepository;
    @Resource
    private PtPhoneHistoryManager ptPhoneHistoryManager;
    @Resource
    private SaleStateCache saleStateCache;

    /**
     * 分析某段时间的已沟通的线索信息和销售信息
     *
     * @return 聚合数据
     */
    public SaleStateVO analyzeSaleState(String begin, String end) {
        SaleStateVO saleStateVO = saleStateCache.getSaleStateVO(begin, end);
        if (saleStateVO != null) {
            return saleStateVO;
        }

        Date beginTime = CommonUtil.beginOfDay(begin);
        Date endTime = CommonUtil.endOfDay(end);

        saleStateVO = new SaleStateVO();
        //时间段总的沟通客户数
        Integer totalContact = ptCustomerStateManager.countDistinctCustomerBetween(0, beginTime, endTime);
        //由技术提供的
        Integer mdTotalContact = ptCustomerStateManager.countDistinctCustomerBetween(1, beginTime, endTime);
        Integer scTotalContact = ptCustomerStateManager.countDistinctCustomerBetween(2, beginTime, endTime);
        Integer otherTotalContact = totalContact - mdTotalContact - scTotalContact;
        saleStateVO.setTotalContact(Arrays.asList(mdTotalContact, otherTotalContact, scTotalContact,
                totalContact));
        saleStateVO.setTotalContactPercent(Arrays.asList(CommonUtil.parsePercent(mdTotalContact, totalContact),
                CommonUtil.parsePercent(otherTotalContact, totalContact),
                CommonUtil.parsePercent(scTotalContact, totalContact)));

        //线索新增量
        Integer addedTotalCount = ptCustomerStateManager.countByOperateTypeAndTimeBetween("新增客户", 0, beginTime,
                endTime);
        Integer mdAddedTotalCount = ptCustomerStateManager.countByOperateTypeAndTimeBetween("新增客户", 1, beginTime,
                endTime);
        Integer scAddedTotalCount = ptCustomerStateManager.countByOperateTypeAndTimeBetween("新增客户", 2, beginTime,
                endTime);
        Integer otherAddedTotalCount = addedTotalCount - mdAddedTotalCount -
                scAddedTotalCount;
        saleStateVO.setAddedContact(Arrays.asList(mdAddedTotalCount, otherAddedTotalCount, scAddedTotalCount,
                addedTotalCount));
        saleStateVO.setAddedContactPercent(Arrays.asList(CommonUtil.parsePercent(mdAddedTotalCount, addedTotalCount),
                CommonUtil.parsePercent(otherAddedTotalCount, addedTotalCount),
                CommonUtil.parsePercent(scAddedTotalCount, addedTotalCount)));

        //接通量，大于0的
        Integer connectedCount = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(0, beginTime, endTime);
        //接通量，大于30秒的
        Integer connected30Count = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(30, beginTime, endTime);
        //接通量，大于60秒的
        Integer connected60Count = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(60, beginTime, endTime);
        //接通量，大于120秒的
        Integer connected120Count = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(120, beginTime, endTime);
        //接通量，大于240秒的
        Integer connected240Count = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(240, beginTime, endTime);
        //总的，包含0的
        Integer totalConnectedCount = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(-1, beginTime, endTime);
        //大于0的
        Integer mdConnectedCount = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(0,
                beginTime, endTime);
        Integer mdConnected30Count = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(30,
                beginTime, endTime);
        Integer mdConnected60Count = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(60,
                beginTime, endTime);
        Integer mdConnected120Count = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(120,
                beginTime, endTime);
        Integer mdConnected240Count = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(240,
                beginTime, endTime);
        //包含0的
        Integer totalMdConnectedCount = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(-1,
                beginTime, endTime);
        Integer scConnectedCount = ptPhoneHistoryManager.findShiChangByCallTimeGreaterThan(0, beginTime, endTime);
        Integer scConnected30Count = ptPhoneHistoryManager.findShiChangByCallTimeGreaterThan(30, beginTime, endTime);
        Integer scConnected60Count = ptPhoneHistoryManager.findShiChangByCallTimeGreaterThan(60, beginTime, endTime);
        Integer scConnected120Count = ptPhoneHistoryManager.findShiChangByCallTimeGreaterThan(120, beginTime, endTime);
        Integer scConnected240Count = ptPhoneHistoryManager.findShiChangByCallTimeGreaterThan(240, beginTime, endTime);
        //其他的包含0的通话量
        Integer totalScConnectedCount = ptPhoneHistoryManager.findShiChangByCallTimeGreaterThan(-1, beginTime,
                endTime);
        Integer otherConnectedCount = connectedCount - mdConnectedCount - scConnectedCount;
        Integer otherConnected30Count = connected30Count - mdConnected30Count - scConnected30Count;
        Integer otherConnected60Count = connected60Count - mdConnected60Count - scConnected60Count;
        Integer otherConnected120Count = connected120Count - mdConnected120Count - scConnected120Count;
        Integer otherConnected240Count = connected240Count - mdConnected240Count - scConnected240Count;

        saleStateVO.setConnectedContact(Arrays.asList(mdConnectedCount, otherConnectedCount, scConnectedCount,
                connectedCount));
        saleStateVO.setConnected30Contact(Arrays.asList(mdConnected30Count, otherConnected30Count, scConnected30Count,
                connected30Count));
        saleStateVO.setConnected60Contact(Arrays.asList(mdConnected60Count, otherConnected60Count, scConnected60Count,
                connected60Count));
        saleStateVO.setConnected120Contact(Arrays.asList(mdConnected120Count, otherConnected120Count,
                scConnected120Count,
                connected120Count));
        saleStateVO.setConnected240Contact(Arrays.asList(mdConnected240Count, otherConnected240Count,
                scConnected240Count,
                connected240Count));

        //其他的通话量，包含0的
        Integer totalOtherConnectedCount = totalConnectedCount - totalMdConnectedCount - totalScConnectedCount;
        //所有的通话量信息，包含0
        saleStateVO.setTotalConnectedContact(Arrays.asList(totalMdConnectedCount, totalOtherConnectedCount,
                totalScConnectedCount, totalConnectedCount));

        //接通率 技术接通量/包含时长为0的接通量
        saleStateVO.setConnectedContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdConnectedCount, totalMdConnectedCount),
                CommonUtil.parsePercent(otherConnectedCount, totalOtherConnectedCount),
                CommonUtil.parsePercent(scConnectedCount, totalScConnectedCount)
        ));
        saleStateVO.setConnected30ContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdConnected30Count, totalMdConnectedCount),
                CommonUtil.parsePercent(otherConnected30Count, totalOtherConnectedCount),
                CommonUtil.parsePercent(scConnected30Count, totalScConnectedCount)
        ));
        saleStateVO.setConnected60ContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdConnected60Count, totalMdConnectedCount),
                CommonUtil.parsePercent(otherConnected60Count, totalOtherConnectedCount),
                CommonUtil.parsePercent(scConnected60Count, totalScConnectedCount)
        ));
        saleStateVO.setConnected120ContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdConnected120Count, totalMdConnectedCount),
                CommonUtil.parsePercent(otherConnected120Count, totalOtherConnectedCount),
                CommonUtil.parsePercent(scConnected120Count, totalScConnectedCount)
        ));
        saleStateVO.setConnected240ContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdConnected240Count, totalMdConnectedCount),
                CommonUtil.parsePercent(otherConnected240Count, totalOtherConnectedCount),
                CommonUtil.parsePercent(scConnected240Count, totalScConnectedCount)
        ));

        //有意向线索量
        Integer intentTotalCount = ptCustomerStateManager.countByIntentedAndTimeBetween(0, 1, beginTime, endTime);
        Integer mdIntentTotalCount = ptCustomerStateManager.countByIntentedAndTimeBetween(1, 1, beginTime,
                endTime);
        Integer scIntentTotalCount = ptCustomerStateManager.countByIntentedAndTimeBetween(2, 1, beginTime,
                endTime);
        Integer otherIntentTotalCount = intentTotalCount - mdIntentTotalCount - scIntentTotalCount;
        saleStateVO.setIntentedContact(Arrays.asList(mdIntentTotalCount, otherIntentTotalCount,
                scIntentTotalCount,
                intentTotalCount));
        //意向率是：技术意向量/技术接通量
        saleStateVO.setIntentedContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdIntentTotalCount, mdTotalContact),
                CommonUtil.parsePercent(otherIntentTotalCount, otherTotalContact),
                CommonUtil.parsePercent(scIntentTotalCount, scTotalContact)));

        //有效沟通量
        Integer validCount = ptPhoneHistoryManager.findTotalCountByCallTimeGreaterThan(30, beginTime, endTime);
        Integer mdValidCount = ptPushSuccessResultRepository.countCallTimeGreaterThanAndStartTimeBetween(30, beginTime,
                endTime);
        Integer scValidCount = ptPhoneHistoryManager.findShiChangByCallTimeGreaterThan(30, beginTime, endTime);
        Integer otherValidCount = validCount - mdValidCount - scValidCount;
        saleStateVO.setValidedContact(Arrays.asList(mdValidCount, otherValidCount, scValidCount, validCount));
        //有效沟通率是：技术有效沟通量 / 技术接通量
        saleStateVO.setValidedContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdValidCount, mdConnectedCount),
                CommonUtil.parsePercent(otherValidCount, otherConnectedCount),
                CommonUtil.parsePercent(scValidCount, scConnectedCount)
        ));

        //成交线索量，customer里status_code为5
        Integer saledTotalCount = ptCustomerStateManager.countByIntentedAndTimeBetween(0, 2, beginTime, endTime);
        Integer mdSaledTotalCount = ptCustomerStateManager.countByIntentedAndTimeBetween(1, 2, beginTime, endTime);
        Integer scSaledTotalCount = ptCustomerStateManager.countByIntentedAndTimeBetween(2, 2, beginTime,
                endTime);
        Integer otherSaledTotalCount = saledTotalCount - mdSaledTotalCount - scSaledTotalCount;
        saleStateVO.setSaledContact(Arrays.asList(mdSaledTotalCount, saledTotalCount - mdSaledTotalCount -
                scSaledTotalCount, scSaledTotalCount, saledTotalCount));
        //成单率是：技术成单量/技术接通量
        saleStateVO.setSaledContactPercent(Arrays.asList(
                CommonUtil.parsePercent(mdSaledTotalCount, mdConnectedCount),
                CommonUtil.parsePercent(otherSaledTotalCount, otherConnectedCount),
                CommonUtil.parsePercent(scSaledTotalCount, scConnectedCount)
        ));

        saleStateCache.saveSaleState(begin, end, saleStateVO);
        return saleStateVO;
    }
}
