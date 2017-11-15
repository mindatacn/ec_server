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
import java.util.Date;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2017/11/1.
 * 用户每日推送数量阈值管理器
 */
@Service
public class PtUserPushThresholdManager extends BaseService {
    @Resource
    private PtUserPushCountRepository ptUserPushCountRepository;
    @Resource
    private PtDepartmentManager ptDepartmentManager;
    @Resource
    private PtUserManager ptUserManager;

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    /**
     * 处理线索推送成功结果
     *
     * @param event
     *         结果vo
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
     * @param userId
     *         用户id
     * @param date
     *         日期
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
        ptUserPushCount.setThreshold(ptDepartmentManager.findByDeptId(ptUser.getDepartmentId()).getThreshold());
        return ptUserPushCountRepository.save(ptUserPushCount);
    }
}
