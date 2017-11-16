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
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
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

    /**
     * 获取当前登录用户的公司CorpId
     *
     * @return id
     */
    public Integer getCorpId() {
        PtCompany ptCompany = findNowCompany();
        if (ptCompany == null) {
            return null;
        } else {
            return ptCompany.getCorpId();
        }
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
        //添加公司管理员
        ptUserManager.addAdmin(companyBody.getAccount(), companyBody.getPassword(), ptCompany.getId());
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
     * 从Ec同步公司信息
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
        eventPublisher.publishEvent(new CompanySyncEvent(force));
        return null;
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
}
