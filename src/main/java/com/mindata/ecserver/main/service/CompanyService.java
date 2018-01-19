package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.response.CompanyData;
import com.mindata.ecserver.ec.model.response.CompanyDeptBean;
import com.mindata.ecserver.ec.model.response.CompanyUserBean;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.CompanyInfoService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.CompanySyncEvent;
import com.mindata.ecserver.main.manager.PtCompanyManager;
import com.mindata.ecserver.main.manager.PtDepartmentManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtCompany;
import com.mindata.ecserver.main.requestbody.CompanyBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.CompanyVO;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
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
     * @param companyBody body
     * @return Company
     */
    @Transactional(rollbackFor = Exception.class)
    public PtCompany addCompany(CompanyBody companyBody) {
        PtCompany ptCompany = ptCompanyManager.add(companyBody);
        //添加公司管理员
        ptUserManager.addAdmin(companyBody.getAccount(), companyBody.getPassword(), companyBody.getRoleId(), ptCompany
                .getId());
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
     * @throws IOException 异常
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
     * @throws IOException EC
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
     * 根据名称模糊查询
     *
     * @param name name
     * @return List
     */
    public List<CompanyVO> findThreshold(String name) {
        List<CompanyVO> companyVOS = new ArrayList<>();
        List<PtCompany> companies = ptCompanyManager.findThreshold(name);
        for (PtCompany ptCompany : companies) {
            CompanyVO companyVO = new CompanyVO();
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
     * @param id        id
     * @param threshold threshold
     * @return PtCompany
     */
    public PtCompany updateThresholdById(Long id, Integer threshold) {
        return ptCompanyManager.updateThresholdById(id, threshold);
    }
}
