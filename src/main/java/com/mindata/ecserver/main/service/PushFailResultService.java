package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.specify.Criteria;
import com.mindata.ecserver.global.specify.Restrictions;
import com.mindata.ecserver.main.manager.EcContactManager;
import com.mindata.ecserver.main.manager.PtPushResultManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.PtPushFailureResult;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.requestbody.PushFailRequestBody;
import com.mindata.ecserver.main.vo.PushFailResultVO;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mindata.ecserver.global.constant.Constant.STATE_FAIL;

/**
 * @author hanliqiang wrote on 2017/11/16
 */
@Service
public class PushFailResultService {
    @Resource
    private PtPushResultManager ptPushResultManager;
    @Resource
    private EcContactManager ecContactManager;
    @Resource
    private PtUserManager ptUserManager;

    /**
     * 查找所有推送失败的记录
     * @param pushFailRequestBody
     * @return
     */
    public SimplePage<PushFailResultVO> findByConditions(PushFailRequestBody pushFailRequestBody) {
        Criteria<PtPushFailureResult> criteria = new Criteria<>();

        //公司名称模糊查询
        if (StrUtil.isNotEmpty(pushFailRequestBody.getCompanyName())) {
            List<EcContactEntity> contactEntityList = ecContactManager.findByStateAndCompanyLike(STATE_FAIL, pushFailRequestBody.getCompanyName());
            List<Long> list = new ArrayList<>();
            for (EcContactEntity ecContactEntity : contactEntityList) {
                list.add(ecContactEntity.getId());
            }
            criteria.add(Restrictions.in("contactId", list, true));
        }
        //推送团队模糊查询
        if (StrUtil.isNotEmpty(pushFailRequestBody.getPushTeam())) {
            List<PtUser> userList = ptUserManager.findByNameLike(pushFailRequestBody.getPushTeam());
            List<Long> teamList = new ArrayList<>();
            for (PtUser ptUser : userList) {
                teamList.add(ptUser.getId());
            }
            criteria.add(Restrictions.in("followUserId", teamList, true));
        }
        //推送人模糊查询
        if (StrUtil.isNotEmpty(pushFailRequestBody.getPushName())) {
            List<PtUser> userList = ptUserManager.findByNameLike(pushFailRequestBody.getPushName());
            List<Long> teamList = new ArrayList<>();
            for (PtUser ptUser : userList) {
                teamList.add(ptUser.getId());
            }
            criteria.add(Restrictions.in("optUserId", teamList, true));
        }
        //开始时间
        if (StrUtil.isNotEmpty(pushFailRequestBody.getBeginTime())) {
            Date date = DateUtil.beginOfDay(DateUtil.parseDate(pushFailRequestBody.getBeginTime()));
            criteria.add(Restrictions.gt("createTime", date, true));
        }
        if (StrUtil.isNotEmpty(pushFailRequestBody.getEndTime())) {
            Date date = DateUtil.endOfDay(DateUtil.parseDate(pushFailRequestBody.getEndTime()));
            criteria.add(Restrictions.lt("createTime", date, true));
        }
        int page = Constant.PAGE_NUM;
        if (pushFailRequestBody.getPage() != null) {
            page = pushFailRequestBody.getPage();
        }
        int size = Constant.PAGE_SIZE;
        if (pushFailRequestBody.getSize() != null) {
            size = pushFailRequestBody.getSize();
        }
        Sort.Direction direction = Constant.DIRECTION;
        if (pushFailRequestBody.getOrder() != null && pushFailRequestBody.getOrder()) {
            direction = Sort.Direction.ASC;
        }
        String orderBy = "createTime";
        if (pushFailRequestBody.getOrderBy() != null) {
            orderBy = pushFailRequestBody.getOrderBy();
        }
        Pageable pageable = new PageRequest(page, size, direction, orderBy);
        Page<PtPushFailureResult> pageFailResults = ptPushResultManager.findFailAll(criteria, pageable);
        List<PushFailResultVO> resultVOS = new ArrayList<>(pageFailResults.getContent().size());
        for (PtPushFailureResult failureResult : pageFailResults) {
            PushFailResultVO failResultVO = new PushFailResultVO();
            EcContactEntity ecContactEntity = ecContactManager.findByIdAndState(failureResult.getContactId(), STATE_FAIL);
            if (ecContactEntity != null) {
                failResultVO.setCompanyName(ecContactEntity.getCompany());
                failResultVO.setId(failureResult.getId());
                failResultVO.setFailReasion(failureResult.getFailureCause());
                failResultVO.setPushTime(failureResult.getCreateTime());
                resultVOS.add(failResultVO);
            }
        }
        return new SimplePage<>(pageFailResults.getTotalPages(), pageFailResults.getTotalElements(), resultVOS);
    }

}
