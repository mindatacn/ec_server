package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtPhoneHistoryCompanyManager;
import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryCompany;
import com.mindata.ecserver.main.vo.PhoneHistoryBeanVO;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/5.
 */
@Service
public class PhoneHistoryCompanyService {
    @Resource
    private PtPhoneHistoryCompanyManager ptPhoneHistoryCompanyManager;


    @SuppressWarnings("Duplicates")
    public PhoneHistoryBeanVO findHistoryByDate(Long companyId, String begin, String end) {
        //不传companyId，则默认是当前用户
        if (companyId == null) {
            companyId = ShiroKit.getCurrentCompanyId();
        }
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));

        //这一段时间的累计数据
        List<Object[]> list = ptPhoneHistoryCompanyManager.findTotalByCompanyId(companyId, beginDate, endDate);
        return new PhoneHistoryBeanVO(list.get(0));
    }

    /**
     * 该接口是生成所有历史数据用的
     */
    public Page<PtPhoneHistoryCompany> fetchAllHistoryData(Long companyId, String begin, String end, Pageable
            pageable, boolean force) throws IOException {
        if (companyId == null) {
            companyId = ShiroKit.getCurrentCompanyId();
        }
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        //分页查询这段时间内的分页数据
        return ptPhoneHistoryCompanyManager.findHistoryByDate(companyId, beginDate, endDate,
                pageable, force);
    }

}
