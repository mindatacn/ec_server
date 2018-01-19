package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.global.specify.Criteria;
import com.mindata.ecserver.global.specify.Restrictions;
import com.mindata.ecserver.main.manager.*;
import com.mindata.ecserver.main.manager.ec.EcCustomerManager;
import com.mindata.ecserver.main.model.secondary.PtPushSuccessResult;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.requestbody.PushResultRequestBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.PushSuccessResultVO;
import com.mindata.ecserver.util.CommonUtil;
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
    @Resource
    private PtPhoneHistoryManager ptPhoneHistoryManager;
    @Resource
    private PtUserManager ptUserManager;
    @Resource
    private PtDepartmentManager ptDepartmentManager;
    @Resource
    private PtRoleManager ptRoleManager;
    @Resource
    private EcCustomerManager ecCustomerManager;


    public PtPushSuccessResult findById(Long id) {
        return ptPushResultManager.findOneSuccess(id);
    }

    @SuppressWarnings("Duplicates")
    public SimplePage<PushSuccessResultVO> findByConditions(PushResultRequestBody
                                                                    pushResultRequestBody) {
        Criteria<PtPushSuccessResult> criteria = new Criteria<>();
        PtUser nowUser = ShiroKit.getCurrentUser();
        //管理员
        if (ptRoleManager.isManager(nowUser.getId())) {
            Long companyId = nowUser.getCompanyId();
            criteria.add(Restrictions.eq("companyId", companyId, true));
        } else {
            criteria.add(Restrictions.eq("followUserId", nowUser.getId(), true));
        }
        //部门领导
        //Long deptId = ShiroKit.getCurrentUser().getDepartmentId();
        //criteria.add(Restrictions.eq("departmentId", deptId, true));

        //开始时间
        if (!StrUtil.isEmpty(pushResultRequestBody.getBeginTime())) {
            Date date = CommonUtil.beginOfDay(pushResultRequestBody.getBeginTime());
            criteria.add(Restrictions.gt("createTime", date, true));
        }
        if (!StrUtil.isEmpty(pushResultRequestBody.getEndTime())) {
            Date date = CommonUtil.endOfDay(pushResultRequestBody.getEndTime());
            criteria.add(Restrictions.lt("createTime", date, true));
        }
        //跟进人查询
        if (CollectionUtil.isNotEmpty(pushResultRequestBody.getFollowUserIds())) {
            criteria.add(Restrictions.in("followUserId", pushResultRequestBody.getFollowUserIds(), true));
        }
        //跟进团队查询
        if (CollectionUtil.isNotEmpty(pushResultRequestBody.getDeptIds())) {
            criteria.add(Restrictions.in("departmentId", pushResultRequestBody.getDeptIds(), true));
        }
        //公司名称模糊查询
        if (!StrUtil.isEmpty(pushResultRequestBody.getCompanyName())) {
            criteria.add(Restrictions.like("companyName", pushResultRequestBody.getCompanyName(), true));
        }
        //沟通结果
        if (pushResultRequestBody.getSaleState() != null) {
            criteria.add(Restrictions.eq("saleState", pushResultRequestBody.getSaleState(), true));
        }
        //电话
        if (!StrUtil.isEmpty(pushResultRequestBody.getMobile())) {
            criteria.add(Restrictions.like("mobile", pushResultRequestBody.getMobile(), true));
        }
        //来源
        if (!CollectionUtil.isEmpty(pushResultRequestBody.getWebsiteIds())) {
            criteria.add(Restrictions.in("websiteId", pushResultRequestBody.getWebsiteIds(), true));
        }
        //行业
        if (!CollectionUtil.isEmpty(pushResultRequestBody.getVocations())) {
            criteria.add(Restrictions.in("vocation", getVocations(pushResultRequestBody.getVocations()), true));
        }
        //区域
        if (!CollectionUtil.isEmpty(pushResultRequestBody.getProvinces())) {
            criteria.add(Restrictions.in("province", pushResultRequestBody.getProvinces(), true));
            List<String> cities = pushResultRequestBody.getCities();
            //如果勾了多个市，则用in
            if (!CollectionUtil.isEmpty(cities)) {
                cities = getCities(pushResultRequestBody.getProvinces(), cities);
                criteria.add(Restrictions.in("city", cities, true));
            }
        }

        int page = Constant.PAGE_NUM;
        if (pushResultRequestBody.getPage() != null) {
            page = pushResultRequestBody.getPage();
        }
        int size = Constant.PAGE_SIZE;
        if (pushResultRequestBody.getSize() != null) {
            size = pushResultRequestBody.getSize();
        }
        Sort.Direction direction = Constant.DIRECTION;
        if (pushResultRequestBody.getOrder() != null && pushResultRequestBody.getOrder()) {
            direction = Sort.Direction.ASC;
        }
        String orderBy = "id";
        if (pushResultRequestBody.getOrderBy() != null) {
            orderBy = pushResultRequestBody.getOrderBy();
        }
        Pageable pageable = new PageRequest(page, size, direction, orderBy);
        Page<PtPushSuccessResult> ptPushSuccessResults = ptPushResultManager.findAll(criteria, pageable);
        List<PushSuccessResultVO> vos = new ArrayList<>(ptPushSuccessResults.getContent().size());
        for (PtPushSuccessResult result : ptPushSuccessResults) {
            PushSuccessResultVO vo = new PushSuccessResultVO();
            vo.setCompany(result.getCompanyName());
            vo.setCreateTime(result.getCreateTime());
            vo.setId(result.getId());
            PtUser ptUser = ptUserManager.findByUserId(result.getFollowUserId());
            vo.setFollowUser(ptUser.getName());
            vo.setFollowDept(ptDepartmentManager.findByDeptId(ptUser.getDepartmentId()).getName());
            vo.setContactDuration(ptPhoneHistoryManager.findTotalContactTimeByCrmId(result.getCrmId()));
            //最后沟通时间
            vo.setLastContactTime(ptPhoneHistoryManager.findByCrmIdOrderByCallTime(result.getCrmId()));
            vo.setContactId(result.getContactId());
            //沟通状态相关的属性
            vo.setSaleState(ecCustomerManager.findCodeByCustomerId(result.getCrmId()));
            vos.add(vo);
        }

        return new SimplePage<>(ptPushSuccessResults.getTotalPages(), ptPushSuccessResults.getTotalElements(), vos);
    }

}
