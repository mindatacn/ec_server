package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.global.specify.Criteria;
import com.mindata.ecserver.global.specify.Restrictions;
import com.mindata.ecserver.main.manager.ec.EcContactManager;
import com.mindata.ecserver.main.manager.PtPushResultManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.PtPushFailureResult;
import com.mindata.ecserver.main.requestbody.PushFailRequestBody;
import com.mindata.ecserver.main.vo.PushFailResultVO;
import com.mindata.ecserver.util.CommonUtil;
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

/**
 * @author hanliqiang wrote on 2017/11/16
 */
@Service
public class PushFailResultService {
    @Resource
    private PtPushResultManager ptPushResultManager;
    @Resource
    private EcContactManager ecContactManager;

    /**
     * 查找所有推送失败的记录
     *
     * @param pushFailRequestBody
     *         body
     * @return SimplePage
     */
    public SimplePage<PushFailResultVO> findByConditions(PushFailRequestBody pushFailRequestBody) {
        Criteria<PtPushFailureResult> criteria = new Criteria<>();

        Long userId = ShiroKit.getCurrentUserId();
        criteria.add(Restrictions.eq("followUserId", userId, true));
        //公司名称模糊查询
        if (StrUtil.isNotEmpty(pushFailRequestBody.getCompanyName())) {
            criteria.add(Restrictions.like("companyName", pushFailRequestBody.getCompanyName(), true));
        }
        //开始时间
        if (StrUtil.isNotEmpty(pushFailRequestBody.getBeginTime())) {
            Date date = CommonUtil.beginOfDay(pushFailRequestBody.getBeginTime());
            criteria.add(Restrictions.gt("createTime", date, true));
        }
        if (StrUtil.isNotEmpty(pushFailRequestBody.getEndTime())) {
            Date date = CommonUtil.endOfDay(pushFailRequestBody.getEndTime());
            criteria.add(Restrictions.lt("createTime", date, true));
        }

        int page = Constant.PAGE_NUM;
        if (pushFailRequestBody.getPage() != null) {
            page = pushFailRequestBody.getPage();
        }
        String orderBy = "id";
        Pageable pageable = new PageRequest(page, Constant.PAGE_SIZE, Sort.Direction.DESC, orderBy);
        Page<PtPushFailureResult> pageFailResults = ptPushResultManager.findFailAll(criteria, pageable);
        List<PushFailResultVO> resultVOS = new ArrayList<>(pageFailResults.getContent().size());
        for (PtPushFailureResult failureResult : pageFailResults) {
            PushFailResultVO failResultVO = new PushFailResultVO();
            EcContactEntity ecContactEntity = ecContactManager.findById(failureResult.getContactId());
            if (ecContactEntity != null) {
                failResultVO.setCompanyName(ecContactEntity.getCompany());
                failResultVO.setId(failureResult.getId());
                failResultVO.setFailReason(failureResult.getFailureCause());
                failResultVO.setPushTime(failureResult.getCreateTime());
                resultVOS.add(failResultVO);
            }
        }
        return new SimplePage<>(pageFailResults.getTotalPages(), pageFailResults.getTotalElements(), resultVOS);
    }

}
