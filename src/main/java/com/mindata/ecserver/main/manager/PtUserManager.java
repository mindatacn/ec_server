package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CompanyUserBean;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.repository.secondary.PtUserRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class PtUserManager {
    @Resource
    private PtUserRepository userRepository;
    @Resource
    private PtRoleManager roleManager;
    @Resource
    private PtDepartmentManager departmentManager;
    @Resource
    private PtCompanyManager companyManager;
    @Resource
    private PtUserRoleManager userRoleManager;

    /**
     * 添加一个公司管理员用户
     *
     * @return 用户
     */
    public PtUser addAdmin(String account, String password, Integer companyId) {
        Date now = CommonUtil.getNow();
        PtUser ptUser = new PtUser();
        ptUser.setCreateTime(now);
        ptUser.setUpdateTime(now);
        ptUser.setAccount(account);
        ptUser.setPassword(CommonUtil.password(password));
        ptUser.setCompanyId(companyId);
        ptUser.setName("公司管理员");
        ptUser.setTitle("公司管理员");
        ptUser = userRepository.save(ptUser);
        //添加role信息
        userRoleManager.add(ptUser.getId(), roleManager.findIdByName(Constant.ROLE_MANAGER));
        return ptUser;
    }

    /**
     * 从Ec同步用户信息
     *
     * @param companyUserBean
     *         ec的user信息
     * @return 本地的user
     */
    public PtUser add(CompanyUserBean companyUserBean, Integer companyId) {
        PtUser ptUser = userRepository.findByEcUserId(companyUserBean.getUserId());
        if (ptUser != null) {
            return ptUser;
        }
        ptUser = new PtUser();
        ptUser.setCreateTime(CommonUtil.getNow());
        ptUser.setUpdateTime(CommonUtil.getNow());
        ptUser.setCompanyId(companyId);
        ptUser.setEcUserId(companyUserBean.getUserId());
        ptUser.setTitle(companyUserBean.getTitle());
        ptUser.setDepartmentId(departmentManager.findByEcDeptId(companyUserBean.getDeptId()).getId());
        ptUser.setName(companyUserBean.getUserName());
        ptUser.setAccount(companyManager.findOne(companyId).getPrefix() + "_" + companyUserBean.getAccount());
        ptUser.setPassword(CommonUtil.password("123456"));
        ptUser.setMobile(companyUserBean.getAccount());

        ptUser = userRepository.save(ptUser);
        //添加role信息
        userRoleManager.add(ptUser.getId(), roleManager.findIdByName(Constant.ROLE_USER));
        return ptUser;
    }

    /**
     * 从Ec同步一批用户数据
     *
     * @param userBeanList
     *         ec用户集合
     * @return 本地集合
     */
    public List<PtUser> addUsers(List<CompanyUserBean> userBeanList, Integer companyId) {
        List<PtUser> ptUsers = new ArrayList<>();
        for (CompanyUserBean userBean : userBeanList) {
            ptUsers.add(add(userBean, companyId));
        }
        return ptUsers;
    }

    /**
     * 根据account查询User
     *
     * @param account
     *         账号名
     * @return 用户
     */
    public PtUser findByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    public PtUser findByUserId(Integer id) {
        return userRepository.findOne(id);
    }
}
