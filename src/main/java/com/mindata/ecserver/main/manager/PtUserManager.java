package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CompanyUserBean;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.repository.secondary.PtUserRepository;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mindata.ecserver.global.constant.Constant.STATE_NORMAL;

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
        //设置不能关联ec账户
        ptUser.setState(1);
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
        ptUser.setState(STATE_NORMAL);
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

    public PtUser findByEcUserId(Long ecUserId) {
        return userRepository.findByEcUserId(ecUserId);
    }

    public PtUser update(PtUser ptUser) {
        return userRepository.save(ptUser);
    }

    /**
     * 统计某个部门的人员数量
     *
     * @param deparmentId
     *         部门id
     * @return 数量
     */
    public Integer countByDepartmentId(Integer deparmentId) {
        return userRepository.countByDepartmentIdAndState(deparmentId, STATE_NORMAL);
    }

    /**
     * 根据部门id，查询所有的员工
     *
     * @param deptId
     *         ec部门id
     * @param state
     *         状态
     * @return user
     */
    public List<PtUser> findByDeptIdAndState(Integer deptId, Integer state) {
        return userRepository.findByDepartmentIdAndState(deptId, state);
    }

    public List<PtUser> findByCompanyIdAndState(Integer companyId, Integer state) {
        return userRepository.findByCompanyIdAndState(companyId, state);
    }

    /**
     * 查询某个部门的员工名字like
     *
     * @param deptId
     *         部门id
     * @param name
     *         名字
     * @return 结果集
     */
    public List<PtUser> findByDeptIdAndNameLike(Integer deptId, String name) {
        if (StrUtil.isEmpty(name)) {
            return userRepository.findByDepartmentIdAndState(deptId, STATE_NORMAL);
        }
        return userRepository.findByDepartmentIdAndStateAndNameLike(deptId, STATE_NORMAL, "%" + name + "%");
    }

    public List<PtUser> findByCompanyIdAndNameLike(Integer companyId, String name) {
        if (StrUtil.isEmpty(name)) {
            return userRepository.findByCompanyIdAndState(companyId, STATE_NORMAL);
        }
        return userRepository.findByCompanyIdAndStateAndNameLike(companyId, STATE_NORMAL, "%" + name + "%");
    }
}
