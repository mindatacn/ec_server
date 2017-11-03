package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryUser;
import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryUserRepository;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 某人某天的通话历史管理类
 */
@Component
public class PtPhoneHistoryUserManager {
    @Resource
    private PtPhoneHistoryUserRepository ptPhoneHistoryUserRepository;
    @Resource
    private PtPhoneHistoryManager ptPhoneHistoryManager;

    /**
     * 查询某天某用户的通话统计信息
     *
     * @param date
     *         某一天
     * @return 统计信息
     */
    public PtPhoneHistoryUser findOneDay(Long ecUserId, Date date) throws IOException {
        Date tempBegin = DateUtil.beginOfDay(date);
        Date tempEnd = DateUtil.endOfDay(date);
        List<PtPhoneHistoryUser> list = ptPhoneHistoryUserRepository.findByEcUserIdAndStartTimeBetween(ecUserId,
                tempBegin,
                tempEnd);
        if (CollectionUtil.isEmpty(list)) {
            //去PhoneHistory总表去查询某天该用户的通话历史
            List<Object[]> ptPhoneHistoryList = ptPhoneHistoryManager.findTotalByEcUserIdAndOneDay(ecUserId, date);
            PtPhoneHistoryUser historyUser = new PtPhoneHistoryUser();
            Object[] objects = ptPhoneHistoryList.get(0);
            historyUser.setEcUserId(ecUserId);
            historyUser.setStartTime(date);
            historyUser.setTotalCallCount((int) objects[0]);
            historyUser.setTotalCallTime((int) objects[1]);
            historyUser.setTotalCustomer((int) objects[2]);
            return ptPhoneHistoryUserRepository.save(historyUser);
        }
        return list.get(0);
    }

    /**
     * 查询某个用户在一段时间内的通话历史集合
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return 分页数据
     */
    public List<PtPhoneHistoryUser> findByEcUserId(Long ecUserId, Date begin, Date end) throws IOException {
        List<PtPhoneHistoryUser> historyUsers = new ArrayList<>();
        Date tempBegin = DateUtil.beginOfDay(begin);
        Date tempEnd = DateUtil.endOfDay(end);
        //如果有缺失，获取begin到end间的所有天，每一天去count表查一次，把缺失的一天数据给补上
        for (; tempBegin.before(tempEnd); tempBegin = DateUtil.offsetDay(tempBegin, 1)) {
            //每一天的
            historyUsers.add(findOneDay(ecUserId, tempBegin));
        }
        return historyUsers;
    }
}
