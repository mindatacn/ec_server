package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtPhoneHistoryDeptManager;
import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryDept;
import com.mindata.ecserver.main.vo.PhoneHistoryBeanVO;
import com.mindata.ecserver.main.vo.PhoneHistoryVO;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/11/5.
 * 部门营销历史数据统计
 */
@Service
public class PhoneHistoryDeptService {
    @Resource
    private PtPhoneHistoryDeptManager ptPhoneHistoryDeptManager;

    /**
     * 部门历史营销统计功能
     */
    @SuppressWarnings("Duplicates")
    public PhoneHistoryVO findDeptHistoryByDateBetween(Integer deptId, String begin, String end, Pageable pageable) {
        if (deptId == null) {
            deptId = ShiroKit.getCurrentUser().getDepartmentId();
        }
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        //这一段时间的累计数据
        List<Object[]> list = ptPhoneHistoryDeptManager.findTotalByDeptId(deptId, beginDate, endDate);
        //sum(totalCallTime), sum(totalCallCount), sum(totalCustomer), sum(pushCount), sum(validCount) " +
        PhoneHistoryVO historyVO = new PhoneHistoryVO();
        PhoneHistoryBeanVO totalVO = new PhoneHistoryBeanVO(list.get(0));
        historyVO.setTotal(totalVO);
        //分页查询这段时间内的分页数据
        Page<PtPhoneHistoryDept> page = ptPhoneHistoryDeptManager.findByDeptId(deptId, beginDate, endDate, pageable);
        List<PhoneHistoryBeanVO> beanVOS = page.getContent().stream().map(ptPhoneHistoryDept -> {
            PhoneHistoryBeanVO vo = new PhoneHistoryBeanVO();
            vo.setPushCount(ptPhoneHistoryDept.getPushCount());
            vo.setTotalCallCount(ptPhoneHistoryDept.getTotalCallCount());
            vo.setValidCount(ptPhoneHistoryDept.getValidCount());
            vo.setTotalCallTime(ptPhoneHistoryDept.getTotalCallTime());
            vo.setTotalCustomer(ptPhoneHistoryDept.getTotalCustomer());
            vo.setDate(DateUtil.formatDate(ptPhoneHistoryDept.getStartTime()));
            return vo;
        }).collect(Collectors.toList());
        SimplePage<PhoneHistoryBeanVO> simplePage = new SimplePage<>(page.getTotalPages(), page.getTotalElements(),
                beanVOS);
        historyVO.setPage(simplePage);
        return historyVO;
    }

}
