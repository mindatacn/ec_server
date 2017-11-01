package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CreateFailureRecord;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.ContactPushResultEvent;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.PtPushFailureResult;
import com.mindata.ecserver.main.model.secondary.PtPushSuccessResult;
import com.mindata.ecserver.main.repository.secondary.PtPushFailureResultRepository;
import com.mindata.ecserver.main.repository.secondary.PtPushSuccessResultRepository;
import com.mindata.ecserver.main.vo.PushResultVO;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 已推送的信息管理
 */
@Service
public class PtPushResultManager {
    @Resource
    private PtPushSuccessResultRepository ptPushSuccessResultRepository;
    @Resource
    private PtPushFailureResultRepository ptPushFailureResultRepository;
    @Resource
    private EcContactManager ecContactManager;

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    /**
     * 处理线索推送失败结果
     *
     * @param event
     *         结果vo
     */
    @EventListener
    public void pushFailureResult(ContactPushResultEvent event) {
        logger.info("开始处理推送失败的线索");
        PushResultVO pushResultVO = (PushResultVO) event.getSource();
        List<CreateFailureRecord> failureRecordList = pushResultVO.getCustomerCreateDataBean().getFailureRecordList();
        if (CollectionUtil.isEmpty(failureRecordList)) {
            logger.info("失败的线索为空");
            return;
        }
        Integer optUserId = pushResultVO.getOptUserId();
        List<Integer> contactIds = pushResultVO.getPushBody().getIds();
        Integer followUserId = pushResultVO.getPushBody().getFollowUserId().intValue();

        for (CreateFailureRecord record : failureRecordList) {
            PtPushFailureResult pushFailureResult = new PtPushFailureResult();
            Integer contactId = contactIds.get(record.getIndex());
            //设置失败的线索id
            pushFailureResult.setContactId(contactId);
            pushFailureResult.setExistedCustomerName(record.getExistedCustomerName());
            pushFailureResult.setExistedFollowUserName(record.getExistedFollowUserName());
            pushFailureResult.setFailureCause(record.getFailureCause());
            pushFailureResult.setFailureFields(record.getFailureFields());
            pushFailureResult.setOptUserId(optUserId);
            pushFailureResult.setFollowUserId(followUserId);
            pushFailureResult.setCreateTime(CommonUtil.getNow());
            pushFailureResult.setUpdateTime(CommonUtil.getNow());
            ptPushFailureResultRepository.save(pushFailureResult);

            //将contact表的state变更为失败
            EcContactEntity contactEntity = ecContactManager.findOne(contactId);
            contactEntity.setState(2);
            ecContactManager.update(contactEntity);
            logger.info("失败的记录有：" + pushFailureResult.toString());
        }
    }

    /**
     * 处理线索推送成功结果
     *
     * @param event
     *         结果vo
     */
    @EventListener
    public void pushSuccessResult(ContactPushResultEvent event) {
        PushResultVO pushResultVO = (PushResultVO) event.getSource();
        Map<String, Long> successCrmIds = pushResultVO.getCustomerCreateDataBean().getSuccessCrmIds();
        if (CollectionUtil.isEmpty(successCrmIds)) {
            logger.info("成功的线索为空");
            return;
        }
        logger.info("开始处理推送成功的线索");
        Integer optUserId = pushResultVO.getOptUserId();
        List<Integer> contactIds = pushResultVO.getPushBody().getIds();
        Integer followUserId = pushResultVO.getPushBody().getFollowUserId().intValue();
        successCrmIds.forEach((key, value) -> {
            Integer index = Integer.valueOf(key);
            Integer crmId = value.intValue();
            EcContactEntity contactEntity = ecContactManager.findOne(contactIds.get(index));
            PtPushSuccessResult result = new PtPushSuccessResult();
            result.setCreateTime(CommonUtil.getNow());
            result.setUpdateTime(CommonUtil.getNow());
            result.setCity(contactEntity.getCity());
            result.setCompanyId(ShiroKit.getCurrentUser().getCompanyId());
            result.setCompanyName(contactEntity.getCompany());
            result.setContactId(contactEntity.getId());
            result.setCrmId(crmId);
            result.setDepartmentId(ShiroKit.getCurrentUser().getDepartmentId());
            result.setFollowUserId(followUserId);
            result.setMobile(contactEntity.getMobile());
            result.setOptUserId(optUserId);
            result.setProvince(contactEntity.getProvince());
            result.setWebsiteId(contactEntity.getWebsiteId());
            //TODO
            result.setVocation1(contactEntity.getVocationTag());
            result.setVocation2(contactEntity.getVocationTag());
            ptPushSuccessResultRepository.save(result);
            //将contact表的state变更为成功
            contactEntity.setState(1);
            ecContactManager.update(contactEntity);
            logger.info("成功的记录有：" + result.toString());
        });


    }

    /**
     * 查看一段时间内的推送总数量
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 总数量
     */
    public int countAllByDate(Date begin, Date end) {
        return ptPushSuccessResultRepository.countByCreateTimeBetween(begin, end) +
                ptPushFailureResultRepository.countByCreateTimeBetween(begin, end);
    }

    public int countSuccessByDate(Date begin, Date end) {
        return ptPushSuccessResultRepository.countByCreateTimeBetween(begin, end);
    }
}
