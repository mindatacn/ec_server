package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtPhoneHistoryUserManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryUser;
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
 */
@Service
public class PhoneHistoryUserService {
    @Resource
    private PtPhoneHistoryUserManager ptPhoneHistoryUserManager;
    @Resource
    private PtUserManager ptUserManager;

    /**
     * 个人历史营销统计功能
     */
    public PhoneHistoryVO findPersonalHistoryByDate(String begin, String end, Pageable pageable) {
        Integer userId = ShiroKit.getCurrentUser().getId();
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        //这一段时间的累计数据
        List<Object[]> list = ptPhoneHistoryUserManager.findTotalByUserId(userId, beginDate, endDate);
        //sum(totalCallTime), sum(totalCallCount), sum(totalCustomer), sum(pushCount), sum(validCount) " +
        PhoneHistoryVO historyVO = new PhoneHistoryVO();
        PhoneHistoryBeanVO totalVO = new PhoneHistoryBeanVO(list.get(0));
        historyVO.setTotal(totalVO);
        //分页查询这段时间内的分页数据
        Page<PtPhoneHistoryUser> page = ptPhoneHistoryUserManager.findByUserId(userId, beginDate, endDate, pageable);
        List<PhoneHistoryBeanVO> beanVOS = page.getContent().stream().map(ptPhoneHistoryUser -> {
            PhoneHistoryBeanVO vo = new PhoneHistoryBeanVO();
            vo.setPushCount(ptPhoneHistoryUser.getPushCount());
            vo.setTotalCallCount(ptPhoneHistoryUser.getTotalCallCount());
            vo.setValidCount(ptPhoneHistoryUser.getValidCount());
            vo.setTotalCallTime(ptPhoneHistoryUser.getTotalCallTime());
            vo.setTotalCustomer(ptPhoneHistoryUser.getTotalCustomer());
            vo.setDate(DateUtil.formatDate(ptPhoneHistoryUser.getStartTime()));
            return vo;
        }).collect(Collectors.toList());
        SimplePage<PhoneHistoryBeanVO> simplePage = new SimplePage<>(page.getTotalPages(), page.getTotalElements(),
                beanVOS);
        historyVO.setPage(simplePage);
        return historyVO;
    }

}
