package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.response.CompanyData;
import com.mindata.ecserver.ec.model.response.CompanyDeptBean;
import com.mindata.ecserver.ec.model.response.CompanyUserBean;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.CompanyInfoService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.global.specify.Criteria;
import com.mindata.ecserver.global.specify.Restrictions;
import com.mindata.ecserver.main.event.CompanyAddEvent;
import com.mindata.ecserver.main.event.CompanySyncEvent;
import com.mindata.ecserver.main.event.OrderChangeEvent;
import com.mindata.ecserver.main.manager.*;
import com.mindata.ecserver.main.manager.ec.EcVocationCodeManager;
import com.mindata.ecserver.main.model.secondary.PtCompany;
import com.mindata.ecserver.main.model.secondary.PtOrder;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.requestbody.CompanyBody;
import com.mindata.ecserver.main.requestbody.CompanyRequestBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.CompanyDetailVO;
import com.mindata.ecserver.main.vo.CompanyThresholdVO;
import com.mindata.ecserver.main.vo.CompanyVO;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.BeanUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class CompanyService extends BaseService {
    @Resource
    private PtCompanyManager ptCompanyManager;
    @Resource
    private PtDepartmentManager ptDepartmentManager;
    @Resource
    private PtUserManager ptUserManager;
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private CallManager callManager;
    @Resource
    private UserService userService;
    @Resource
    private PtOrderManager ptOrderManager;
    @Resource
    private PtProductManager ptProductManager;
    @Resource
    private PtRoleManager ptRoleManager;
    @Resource
    private EcVocationCodeManager ecVocationCodeManager;

    /**
     * 获取当前登录用户的公司CorpId
     *
     * @return id
     */
    public Long getCorpId() {
        PtCompany ptCompany = findNowCompany();
        return ptCompany == null ? null : ptCompany.getCorpId();
    }

    /**
     * 添加一个Company
     *
     * @param companyBody
     *         body
     * @return Company
     */
    @Transactional(rollbackFor = Exception.class)
    public PtCompany addCompany(CompanyBody companyBody) {
        PtCompany ptCompany = ptCompanyManager.add(companyBody);

        companyBody.setId(ptCompany.getId());
        //发布事件
        eventPublisher.publishEvent(new CompanyAddEvent(companyBody));
        return ptCompany;
    }

    /**
     * 获取当前的公司
     *
     * @return Company
     */
    public PtCompany findNowCompany() {
        Long companyId = ShiroKit.getCurrentUser().getCompanyId();
        return ptCompanyManager.findOne(companyId);
    }

    /**
     * 从Ec同步公司信息，返回新增的用户列表集合
     *
     * @return CompanyData
     * @throws IOException
     *         异常
     */
    @Transactional(rollbackFor = Exception.class)
    public Object syncFromEc(Boolean force) throws IOException {
        if (force == null) {
            force = false;
        }
        Long companyId = ShiroKit.getCurrentUser().getCompanyId();
        // 同步之前的最大id
        Long beforeUserId = ptUserManager.findCompanyMaxUserId(companyId);
        eventPublisher.publishEvent(new CompanySyncEvent(force));
        // 缓存同步之后的最大id
        Long afterUserId = ptUserManager.findCompanyMaxUserId(companyId);

        return userService.findByIdBetween(beforeUserId, afterUserId, companyId);
    }

    /**
     * 监听公司同步信息
     *
     * @throws IOException
     *         EC
     */
    @EventListener(CompanySyncEvent.class)
    public void listen(CompanySyncEvent syncEvent) throws IOException {
        Boolean force = (Boolean) syncEvent.getSource();
        CompanyInfoService companyInfoService = serviceBuilder.getCompanyInfoService();
        CompanyData companyData = (CompanyData) callManager.execute(companyInfoService.getCompanyInfo());
        //根据CompanyData往本地Company插值
        List<CompanyDeptBean> deptBeanList = companyData.getData().getDepts();
        List<CompanyUserBean> userBeanList = companyData.getData().getUsers();
        Long companyId = ShiroKit.getCurrentUser().getCompanyId();
        ptDepartmentManager.addDepts(deptBeanList, companyId, force);
        ptUserManager.addUsers(userBeanList, companyId, force);
    }

    /**
     * 订单状态有变化时，修改Company的status和buyState
     *
     * @param orderChangeEvent
     *         orderChangeEvent
     */
    @EventListener(OrderChangeEvent.class)
    public void orderChange(OrderChangeEvent orderChangeEvent) {
        Long companyId = (Long) orderChangeEvent.getSource();
        PtCompany ptCompany = ptCompanyManager.findOne(companyId);
        PtOrder ptOrder = ptOrderManager.findNewOrderByCompanyId(companyId);
        Date now = CommonUtil.getNow();
        if (now.after(ptOrder.getExpiryDate())) {
            //已过期
            ptCompany.setBuyStatus(4);
            //欠费
            ptCompany.setStatus(1);
            ptCompanyManager.update(ptCompany);
        } else if (DateUtil.betweenDay(now, ptOrder.getExpiryDate(), true) <= 30) {
            //快过期
            ptCompany.setBuyStatus(3);
            ptCompany.setStatus(0);
            ptCompanyManager.update(ptCompany);
        } else if (ptOrderManager.countByCompanyId(companyId) > 1) {
            // 已续费
            ptCompany.setBuyStatus(2);
            ptCompany.setStatus(0);
        } else {
            //新购买
            ptCompany.setBuyStatus(1);
            ptCompany.setStatus(0);
        }

        ptCompanyManager.update(ptCompany);
    }

    /**
     * 根据名称模糊查询
     *
     * @param name
     *         name
     * @return List
     */
    public List<CompanyThresholdVO> findThreshold(String name) {
        List<CompanyThresholdVO> companyVOS = new ArrayList<>();
        List<PtCompany> companies = ptCompanyManager.findThreshold(name);
        for (PtCompany ptCompany : companies) {
            CompanyThresholdVO companyVO = new CompanyThresholdVO();
            companyVO.setId(ptCompany.getId());
            companyVO.setName(ptCompany.getName());
            companyVO.setThreshold(ptCompany.getThreshold());
            companyVOS.add(companyVO);
        }
        return companyVOS;
    }

    /**
     * 根据id修改阈值
     *
     * @param id
     *         id
     * @param threshold
     *         threshold
     * @return PtCompany
     */
    public PtCompany updateThresholdById(Long id, Integer threshold) {
        return ptCompanyManager.updateThresholdById(id, threshold);
    }

    /**
     * 根据条件查询公司信息
     *
     * @param companyRequestBody
     *         companyRequestBody
     * @return SimplePage
     */
    public SimplePage<CompanyVO> findByConditions(CompanyRequestBody companyRequestBody) {
        Criteria<PtCompany> criteria = new Criteria<>();
        //公司名称模糊查询
        if (StrUtil.isNotEmpty(companyRequestBody.getName())) {
            criteria.add(Restrictions.like("name", companyRequestBody.getName(), true));
        }
        if (companyRequestBody.getBuyStatus() != null) {
            criteria.add(Restrictions.eq("buyStatus", companyRequestBody.getBuyStatus(), true));
        }
        if (companyRequestBody.getProductId() != null) {
            criteria.add(Restrictions.eq("productId", companyRequestBody.getProductId(), true));
        }
        int page = Constant.PAGE_NUM;
        if (companyRequestBody.getPage() != null) {
            page = companyRequestBody.getPage();
        }
        String orderBy = "id";
        Pageable pageable = new PageRequest(page, Constant.PAGE_SIZE, Sort.Direction.DESC, orderBy);
        Page<PtCompany> ptCompanies = ptCompanyManager.findAll(criteria, pageable);
        List<CompanyVO> vos = new ArrayList<>(ptCompanies.getContent().size());
        for (PtCompany ptCompany : ptCompanies) {
            CompanyVO companyVO = new CompanyVO();
            companyVO.setId(ptCompany.getId());
            companyVO.setProductName(ptProductManager.findProductNameById(ptCompany.getProductId()));
            companyVO.setCompanyName(ptCompany.getName());
            companyVO.setContactPerson(ptCompany.getContactPerson());
            PtUser ptUser = userService.findManagerUser(ptCompany.getId());
            companyVO.setAccount(ptUser.getAccount());
            companyVO.setRoleName(ptRoleManager.findManagerRoleName(ptUser.getId()));
            companyVO.setBuyStatus(ptCompany.getBuyStatus());
            vos.add(companyVO);
        }
        return new SimplePage<>(ptCompanies.getTotalPages(), ptCompanies.getTotalElements(), vos);
    }

    /**
     * 查看客户详情
     */
    public CompanyDetailVO findCompanyDetailById(Long companyId) {
        CompanyDetailVO companyVO = new CompanyDetailVO();
        PtCompany ptCompany = ptCompanyManager.findOne(companyId);

        BeanUtil.copyProperties(ptCompany, companyVO);

        companyVO.setProductName(ptProductManager.findProductNameById(ptCompany.getProductId()));

        PtOrder ptOrder = ptOrderManager.findNewOrderByCompanyId(companyId);
        companyVO.setEffectiveDate(ptOrder.getEffectiveDate());
        companyVO.setExpiryDate(ptOrder.getExpiryDate());

        PtUser ptUser = userService.findManagerUser(ptCompany.getId());
        companyVO.setAccount(ptUser.getAccount());
        companyVO.setRoleName(ptRoleManager.findManagerRoleName(ptUser.getId()));

        companyVO.setVocation(ecVocationCodeManager.findNameByCode(ptCompany.getVocationTag()));
        
        return companyVO;
    }

    /**
     * 修改公司信息
     *
     * @param companyBody
     *         companyBody
     */
    public PtCompany updateCompanyById(CompanyBody companyBody) {
        PtCompany ptCompany = ptCompanyManager.update(companyBody);

        PtUser ptUser = userService.findManagerUser(companyBody.getId());
        if (StrUtil.isNotEmpty(companyBody.getAccount()) && !StrUtil.equals(ptUser.getAccount(), companyBody
                .getAccount())) {
            ptUser.setAccount(companyBody.getAccount());
            ptUserManager.update(ptUser);
        }
        if (StrUtil.isNotEmpty(companyBody.getPassword())) {
            ptUser.setPassword(CommonUtil.password(companyBody.getPassword()));
            ptUserManager.update(ptUser);
        }
        return ptCompany;
    }

    public boolean delete(Long companyId) {
        //最后的订单是否过期
        if (!ptOrderManager.isExpire(companyId)) {
            return false;
        }
        eventPublisher.publishEvent(new OrderChangeEvent(companyId));
        return true;
    }

    /**
     * 定时修改购买状态
     */
    public void timingUpdateStatus() {
        //查所有没过期的Company
        List<PtCompany> companies = ptCompanyManager.findByBuyStatusNot(4);
        for (PtCompany ptCompany : companies) {
            eventPublisher.publishEvent(new OrderChangeEvent(ptCompany.getId()));
        }
    }
}
