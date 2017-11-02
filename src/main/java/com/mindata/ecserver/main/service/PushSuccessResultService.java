package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.specify.Criteria;
import com.mindata.ecserver.global.specify.Restrictions;
import com.mindata.ecserver.main.manager.PtPushResultManager;
import com.mindata.ecserver.main.model.secondary.PtPushSuccessResult;
import com.mindata.ecserver.main.requestbody.PushResultRequestBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.PushSuccessResultVO;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
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
 * @author wuweifeng wrote on 2017/10/26.
 * 条件查询联系人数据（推送成功的）
 */
@Service
public class PushSuccessResultService extends BaseService {
    @Resource
    private PtPushResultManager ptPushResultManager;

    public PtPushSuccessResult findById(int id) {
        return ptPushResultManager.findOneSuccess(id);
    }

    @SuppressWarnings("Duplicates")
    public SimplePage<PushSuccessResultVO> findByConditions(PushResultRequestBody
                                                                    contactRequestBody) {
        Criteria<PtPushSuccessResult> criteria = new Criteria<>();
        //开始时间
        if (!StrUtil.isEmpty(contactRequestBody.getBeginTime())) {
            Date date = DateUtil.beginOfDay(DateUtil.parseDate(contactRequestBody.getBeginTime()));
            criteria.add(Restrictions.gt("createTime", date, true));
        }
        if (!StrUtil.isEmpty(contactRequestBody.getEndTime())) {
            Date date = DateUtil.endOfDay(DateUtil.parseDate(contactRequestBody.getEndTime()));
            criteria.add(Restrictions.lt("createTime", date, true));
        }
        //公司名称模糊查询
        if (!StrUtil.isEmpty(contactRequestBody.getCompanyName())) {
            criteria.add(Restrictions.like("companyName", contactRequestBody.getCompanyName(), true));
        }
        //沟通结果
        if (contactRequestBody.getSaleState() != null) {
            criteria.add(Restrictions.eq("saleState", contactRequestBody.getSaleState(), true));
        }
        //电话
        if (!StrUtil.isEmpty(contactRequestBody.getMobile())) {
            criteria.add(Restrictions.like("mobile", contactRequestBody.getMobile(), true));
        }
        //来源
        if (!CollectionUtil.isEmpty(contactRequestBody.getWebsiteIds())) {
            criteria.add(Restrictions.in("websiteId", contactRequestBody.getWebsiteIds(), true));
        }
        //行业
        if (!CollectionUtil.isEmpty(contactRequestBody.getVocations())) {
            criteria.add(Restrictions.in("vocation", getVocations(contactRequestBody.getVocations()), true));
        }
        //区域
        if (!CollectionUtil.isEmpty(contactRequestBody.getProvinces())) {
            criteria.add(Restrictions.in("province", contactRequestBody.getProvinces(), true));
            List<String> cities = contactRequestBody.getCities();
            //如果勾了多个市，则用in
            if (!CollectionUtil.isEmpty(cities)) {
                cities = getCities(contactRequestBody.getProvinces(), cities);
                criteria.add(Restrictions.in("city", cities, true));
            }
        }

        int page = Constant.PAGE_NUM;
        if (contactRequestBody.getPage() != null) {
            page = contactRequestBody.getPage();
        }
        int size = Constant.PAGE_SIZE;
        if (contactRequestBody.getSize() != null) {
            size = contactRequestBody.getSize();
        }
        Sort.Direction direction = Constant.DIRECTION;
        if (contactRequestBody.getOrder() != null && contactRequestBody.getOrder()) {
            direction = Sort.Direction.ASC;
        }
        String orderBy = "createTime";
        if (contactRequestBody.getOrderBy() != null) {
            orderBy = contactRequestBody.getOrderBy();
        }
        Pageable pageable = new PageRequest(page, size, direction, orderBy);
        Page<PtPushSuccessResult> ptPushSuccessResults = ptPushResultManager.findAll(criteria, pageable);
        List<PushSuccessResultVO> vos = new ArrayList<>(ptPushSuccessResults.getContent().size());
        for (PtPushSuccessResult result : ptPushSuccessResults) {
            PushSuccessResultVO vo = new PushSuccessResultVO();
            vo.setCompany(result.getCompanyName());
            vo.setCreateTime(result.getCreateTime());
            vo.setId(result.getId());
            //TODO 沟通相关的属性
            vos.add(vo);
        }

        return new SimplePage<>(ptPushSuccessResults.getTotalPages(), ptPushSuccessResults.getTotalElements(), vos);
    }

}
