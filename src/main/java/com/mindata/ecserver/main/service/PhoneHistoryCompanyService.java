package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.manager.PtPhoneHistoryCompanyManager;
import com.mindata.ecserver.main.manager.PtPhoneHistoryUserManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryCompany;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.vo.PhoneHistoryBeanVO;
import com.mindata.ecserver.main.vo.PhoneHistoryUserBeanVO;
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


    @SuppressWarnings("Duplicates")
    public PhoneHistoryBeanVO findHistoryByDate(Long companyId, String begin, String end) {
        //不传companyId，则默认是当前用户
        if (companyId == null) {
            companyId = ShiroKit.getCurrentUser().getCompanyId();
        }
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));

        //这一段时间的累计数据
        List<Object[]> list = ptPhoneHistoryCompanyManager.findTotalByCompanyId(companyId, beginDate, endDate);
        return new PhoneHistoryBeanVO(list.get(0));
        //List<PhoneHistoryBeanVO> beanVOS = page.getContent().stream().map(ptPhoneHistoryDept -> {
        //    PhoneHistoryBeanVO vo = new PhoneHistoryBeanVO();
        //    vo.setPushCount(ptPhoneHistoryDept.getPushCount());
        //    vo.setTotalCallCount(ptPhoneHistoryDept.getTotalCallCount());
        //    vo.setValidCount(ptPhoneHistoryDept.getValidCount());
        //    vo.setTotalCallTime(ptPhoneHistoryDept.getTotalCallTime());
        //    vo.setTotalCustomer(ptPhoneHistoryDept.getTotalCustomer());
        //    return vo;
        //}).collect(Collectors.toList());
    }

    /**
     * 该接口是生成所有历史数据用的
     */
    public Page<PtPhoneHistoryCompany> fetchAllHistoryData(Long companyId, String begin, String end, Pageable
            pageable, boolean force) throws IOException {
        if (companyId == null) {
            companyId = ShiroKit.getCurrentUser().getCompanyId();
        }
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        //分页查询这段时间内的分页数据
        return ptPhoneHistoryCompanyManager.findHistoryByDate(companyId, beginDate, endDate,
                pageable, force);
    }

    /**
     * 给HR用的功能
     */
    public List<UserHistoryStateVO> findHistoryByDate(String begin, String end) {
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate(end));
        List<PtUser> userList = ptUserManager.findByCompanyIdAndState(1L, 0);
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


    /**
     * 统计某段时间内所有人的通话效果
     */
    @Resource
    private PhoneHistoryUserService phoneHistoryUserService;

    public void tongji() {
        Date beginDate = DateUtil.beginOfDay(DateUtil.parseDate("2017-11-01"));
        Date endDate = DateUtil.endOfDay(DateUtil.parseDate("2017-11-23"));
        List<PhoneHistoryUserBeanVO> vos1 = phoneHistoryUserService.findPersonalHistoryByCompanyId(1L, beginDate,
                endDate);
        DecimalFormat df = new DecimalFormat("######0.00");
        System.out.println("姓名,总拨打次数,麦达拨打次数,总沟通人数,麦达的沟通人数,其他的沟通人数,时长为0的拨打,时长大于0的拨打,麦达的时长大于0的拨打," +
                "有效拨打,麦达有效拨打,总沟通时长,麦达总通话时间");
        for (PhoneHistoryUserBeanVO vo : vos1) {
            String s;
            if (vo.getTotalCallCount() != 0) {
                s = df.format(vo.getValidCount() * 100.0 / vo.getTotalCallCount()) + "%,";
            } else {
                s = "0%,";
            }
            String a;
            //麦达有效沟通率
            if (vo.getPushCount() != 0) {
                a = df.format(vo.getPushValidCount() * 100.0 / vo.getPushCount()) + "%,";
            } else {
                a = "0%,";
            }
            System.out.println(
                    vo.getUserName() + ","
                            + vo.getTotalCallCount() + ","    //总拨打次数
                            + vo.getPushCount() + ","  //麦达的拨打次数
                            + vo.getTotalCustomer() + "," //总沟通人数
                            + vo.getPushCustomer() + "," //麦达的沟通人数
                            + (vo.getTotalCustomer() - vo.getPushCustomer()) + "," //其他的沟通人数
                            + (vo.getTotalCallCount() - vo.getValidCount()) + "," //时长为0的拨打
                            + vo.getValidCount() + "," //时长大于0的拨打
                            + vo.getPushValidCount() + "," //麦达的时长大于0的拨打
                            + s //有效拨打
                            + a
                            + vo.getTotalCallTime() + ","  //总沟通时长
                            + vo.getPushCallTime() + "" //麦达总通话时间

            );
        }
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
