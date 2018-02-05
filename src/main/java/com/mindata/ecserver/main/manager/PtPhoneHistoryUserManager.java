package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryUser;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryUserRepository;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.constant.Constant.STATE_NORMAL;

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
    @Resource
    private PtUserManager ptUserManager;

    /**
     * 查询某天某个部门所有员工的电话统计
     *
     * @param deptId
     *         部门id
     * @param begin
     *         一天开始时间
     * @param end
     *         一天结束时间
     * @return 数量集合
     */
    public List<Object[]> findDeptOneDayTotalByDeptId(Long deptId, Date begin, Date end, boolean force) throws
            IOException {
        //找到部门所有正常的员工
        List<PtUser> users = ptUserManager.findByDeptIdAndState(deptId, STATE_NORMAL);
        List<Long> ids = users.stream().map(PtUser::getId).collect(Collectors.toList());

        List<Object[]> list = new ArrayList<>();
        long totalCallTime = 0L, totalCallCount = 0L, totalCustomer = 0L, pushCount = 0L, validCount = 0, noPushCount
                = 0, pushCallTime = 0, pushCustomer = 0, pushValidCount = 0;
        for (Long id : ids) {
            PtPhoneHistoryUser user = findOneDay(id, begin, end, force);
            totalCallTime += user.getTotalCallTime();
            totalCallCount += user.getTotalCallCount();
            totalCustomer += user.getTotalCustomer();
            pushCount += user.getPushCount();
            validCount += user.getValidCount();
            noPushCount += user.getNoPushCount();
            pushCallTime += user.getPushCallTime();
            pushCustomer += user.getPushCustomer();
            pushValidCount += user.getPushValidCount();
        }
        Object[] objects = new Object[]{totalCallTime, totalCallCount, totalCustomer, pushCount, validCount,
                noPushCount, pushCallTime, pushCustomer, pushValidCount};
        list.add(objects);
        //得到该天的累计数量
        return list;
    }

    /**
     * 查询某用户一段时间内的统计信息
     *
     * @param userId
     * userId
     * @param begin
     * begin
     * @param end
     * end
     * @return
     * 统计
     */
    public List<Object[]> findTotalByUserId(Long userId, Date begin, Date end) {
        return ptPhoneHistoryUserRepository.findCount(CollectionUtil.newArrayList(userId), begin, end);
    }

    /**
     * 查询某天某用户的通话统计信息
     *
     * @return 统计信息
     */
    private PtPhoneHistoryUser findOneDay(Long userId, Date tempBegin, Date tempEnd, boolean force) throws IOException {
        List<PtPhoneHistoryUser> list = ptPhoneHistoryUserRepository.findByUserIdAndStartTimeBetween(userId,
                tempBegin,
                tempEnd);
        if (CollectionUtil.isEmpty(list) || force) {
            //去PhoneHistory总表去查询某天该用户的通话历史
            List<Object[]> ptPhoneHistoryList = ptPhoneHistoryManager.findTotalByUserIdAndOneDay(userId,
                    tempBegin, tempEnd);
            PtPhoneHistoryUser historyUser;
            if (CollectionUtil.isEmpty(list)) {
                historyUser = new PtPhoneHistoryUser();
            } else {
                historyUser = list.get(0);
            }

            Object[] objects = ptPhoneHistoryList.get(0);
            historyUser.setCreateTime(CommonUtil.getNow());
            historyUser.setUpdateTime(CommonUtil.getNow());
            historyUser.setUserId(userId);
            historyUser.setStartTime(tempBegin);
            historyUser.setTotalCallCount(CommonUtil.parseObject(objects[0]));
            historyUser.setTotalCallTime(CommonUtil.parseObject(objects[1]));
            historyUser.setTotalCustomer(CommonUtil.parseObject(objects[2]));
            historyUser.setPushCount(CommonUtil.parseObject(objects[3]));
            historyUser.setValidCount(CommonUtil.parseObject(objects[4]));
            historyUser.setNoPushCount(CommonUtil.parseObject(objects[5]));
            historyUser.setPushCallTime(CommonUtil.parseObject(objects[6]));
            historyUser.setPushCustomer(CommonUtil.parseObject(objects[7]));
            historyUser.setPushValidCount(CommonUtil.parseObject(objects[8]));
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
    public Page<PtPhoneHistoryUser> findByUserId(Long userId, Date begin, Date end, Pageable pageable) {
        return ptPhoneHistoryUserRepository.findByUserIdAndStartTimeBetween(userId, begin, end, pageable);
    }
}
