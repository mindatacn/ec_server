package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtPhoneHistoryCompanyManager;
import com.mindata.ecserver.main.manager.PtPhoneHistoryUserManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryCompany;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.vo.UserHistoryStateVO;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/5.
 */
@Service
public class PhoneHistoryCompanyService {
    @Resource
    private PtPhoneHistoryCompanyManager ptPhoneHistoryCompanyManager;
    @Resource
    private PtPhoneHistoryUserManager ptPhoneHistoryUserManager;
    @Resource
    private PtUserManager ptUserManager;

    public SimplePage findHistoryByDate(Integer companyId, String begin, String end, Pageable pageable) throws
            IOException {
        //不传companyId，则默认是当前用户
        if (companyId == null) {
            companyId = ShiroKit.getCurrentUser().getCompanyId();
        }
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));

        Page<PtPhoneHistoryCompany> page = ptPhoneHistoryCompanyManager.findHistoryByDate(companyId, beginDate,
                endDate, pageable);

        List<PtPhoneHistoryCompany> companies = page.getContent();


        return null;
    }

    /**
     * 给HR用的功能
     *
     * @param begin
     * @param end
     * @return
     */
    public List<UserHistoryStateVO> findHistoryByDate(String begin, String end) {
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        List<PtUser> userList = ptUserManager.findByCompanyIdAndState(1, 0);
        List<UserHistoryStateVO> vos = new ArrayList<>();
        for (PtUser ptUser : userList) {
            List<Object[]> objects = ptPhoneHistoryUserManager.findTotalByUserId(ptUser.getId(), beginDate, endDate);
            Object[] obs = objects.get(0);
            if (obs[0] == null) {
                continue;
            }
            if (ptUser.getDepartmentId() == 2 || ptUser.getDepartmentId() == 3
                    || ptUser.getDepartmentId() == 6 || ptUser.getDepartmentId() == 7 || ptUser.getDepartmentId() == 10
                    || ptUser.getDepartmentId() == 11) {
                UserHistoryStateVO vo = new UserHistoryStateVO();
                vo.setName(ptUser.getName());
                int totalCallTime = Integer.valueOf(obs[0].toString());
                vo.setHasDone(CommonUtil.secToTime(totalCallTime));
                vo.setShouldDone("60分钟");
                vo.setNoDone(CommonUtil.secToTime((3600 - totalCallTime)));
                DecimalFormat df = new DecimalFormat("######0.00");
                double scale = totalCallTime * 100.0 / 3600;
                double noscale = (3600 - totalCallTime) * 100.0 / 3600;
                vo.setScale(df.format(scale) + "%");
                vo.setNoScale(df.format(noscale) + "%");
                vos.add(vo);
            }
        }
        return vos;
    }

    public List<String> findHistoryStrByDate(String begin, String end) {
        List<UserHistoryStateVO> vos = findHistoryByDate(begin, end);
        List<String> list = new ArrayList<>();
        for (UserHistoryStateVO vo : vos) {
            list.add(vo.getName() + "," + vo.getShouldDone() + "," + vo.getHasDone() + "," + vo.getNoDone() + "," +
                    vo.getScale() + "," + vo.getNoScale());
        }
        return list;
    }

}