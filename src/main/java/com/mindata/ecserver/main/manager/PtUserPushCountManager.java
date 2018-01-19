package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.event.ContactPushResultEvent;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.model.secondary.PtUserPushCount;
import com.mindata.ecserver.main.repository.secondary.PtUserPushCountRepository;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.PushResultVO;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wuweifeng wrote on 2017/11/1.
 * 用户每日推送数量管理器
 */
@Service
public class PtUserPushCountManager extends BaseService {
    @Resource
    private PtUserPushCountRepository ptUserPushCountRepository;
    @Resource
    private PtUserManager ptUserManager;

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    /**
     * 处理线索推送成功结果
     *
     * @param event 结果vo
     */
    @EventListener
    public void pushSuccessResult(ContactPushResultEvent event) {
        logger.info("开始计算用户今日的剩余推送阈值");
        PushResultVO pushResultVO = (PushResultVO) event.getSource();
        Map<String, Long> successCrmIds = pushResultVO.getCustomerCreateDataBean().getSuccessCrmIds();
        if (CollectionUtil.isEmpty(successCrmIds)) {
            logger.info("成功的线索为空");
            return;
        }
        PtUserPushCount ptUserPushCount = findCountByUserId(pushResultVO.getPushBody().getFollowUserId(),
                null);
        ptUserPushCount.setPushedCount(ptUserPushCount.getPushedCount() + successCrmIds.size());
        ptUserPushCountRepository.save(ptUserPushCount);
        logger.info("用户剩余推送数量为：" + (ptUserPushCount.getThreshold() - ptUserPushCount.getPushedCount()));
    }

    /**
     * 查询某天某用户的推送数量
     *
     * @param userId 用户id
     * @param date   日期
     * @return 结果
     */
    public PtUserPushCount findCountByUserId(Long userId, Date date) {
        if (date == null) {
            date = CommonUtil.getNow();
        }
        Date begin = DateUtil.beginOfDay(date);
        Date end = DateUtil.endOfDay(date);
        //查询今天某用户的推送情况
        PtUserPushCount ptUserPushCount = ptUserPushCountRepository.findByUserIdAndPushDateBetween(userId, begin, end);
        //如果今天没数据
        if (ptUserPushCount == null) {
            return add(userId, date);
        }
        return ptUserPushCount;
    }

    /**
     * 添加一条推送数量的实例，阈值从上级获取
     *
     * @return 添加的实例
     */
    public PtUserPushCount add(Long userId, Date date) {
        PtUserPushCount ptUserPushCount = new PtUserPushCount();
        ptUserPushCount.setCreateTime(date);
        ptUserPushCount.setUpdateTime(date);
        ptUserPushCount.setPushDate(date);

        PtUser ptUser = ptUserManager.findByUserId(userId);
        ptUserPushCount.setDepartmentId(ptUser.getDepartmentId());
        ptUserPushCount.setCompanyId(ptUser.getCompanyId());
        ptUserPushCount.setPushedCount(0);
        ptUserPushCount.setUserId(userId);
        // 之前从dept里获取 现在改为从user里获取
        ptUserPushCount.setThreshold(ptUser.getThreshold());
        return ptUserPushCountRepository.save(ptUserPushCount);
    }

    /**
     * 按天查询每天的累计
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 结果
     */
    public List<Map<String, Object>> findByPushDateTime(String begin, String end) {
        List<Map<String, Object>> list = new ArrayList<>();
        Date beginTime = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endTime = DateUtil.endOfDay(DateUtil.parseDate(end));
        for (; beginTime.before(endTime); beginTime = DateUtil.offsetDay(beginTime, 1)) {
            //查一天的统计
            Date oneDayEnd = DateUtil.endOfDay(beginTime);
            List<Object[]> objects = ptUserPushCountRepository.findByOneDayBetween(beginTime, oneDayEnd);
            Map<String, Object> map = new HashMap<>(4);
            map.put("pushDate", beginTime);
            if (objects.get(0)[0] == null) {
                map.put("total", 0);
            } else {
                map.put("total", CommonUtil.parseObject(objects.get(0)[0]));
            }
            map.put("user", objects.get(0)[1]);
            list.add(map);
        }
        return list;
    }

    /**
     * 查询某公司当天推送的总和
     *
     * @param companyId companyId
     * @return Integer
     */
    public Integer getPushedCountSum(Long companyId) {
        Date beginTime = DateUtil.beginOfDay(CommonUtil.getNow());
        Date endTime = DateUtil.endOfDay(CommonUtil.getNow());
        Integer count = ptUserPushCountRepository.getPushedCountSum(companyId, beginTime, endTime);
        if (count == null) {
            return 0;
        }
        return count;
    }
}
