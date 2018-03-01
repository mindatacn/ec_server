package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CompanyUserBean;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.CompanyAddEvent;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.repository.secondary.PtUserRepository;
import com.mindata.ecserver.main.requestbody.CompanyBody;
import com.mindata.ecserver.main.requestbody.UserBody;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.BeanUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.constant.Constant.STATE_DELETE;
import static com.mindata.ecserver.global.constant.Constant.STATE_NORMAL;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class PtUserManager {
    @Resource
    private PtUserRepository userRepository;
    @Resource
    private PtDepartmentManager departmentManager;
    @Resource
    private PtCompanyManager companyManager;
    @Resource
    private PtUserRoleManager userRoleManager;

    /**
     * 添加一个公司管理员用户
     */
    private void addAdmin(String account, String password, Long roleId, Long companyId) {
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
        userRoleManager.add(ptUser.getId(), roleId);
    }

    /**
     * 添加一个成员
     *
     * @return ptUser
     */
    public PtUser add(UserBody userBody) {
        Date now = CommonUtil.getNow();
        PtUser ptUser = new PtUser();
        BeanUtil.copyProperties(userBody, ptUser);
        ptUser.setCreateTime(now);
        ptUser.setUpdateTime(now);
        ptUser.setState(STATE_NORMAL);
        ptUser.setPassword(CommonUtil.password(userBody.getPassword()));
        Long companyId = ShiroKit.getCurrentCompanyId();
        ptUser.setCompanyId(companyId);
        if (userBody.getThreshold() == null) {
            ptUser.setThreshold(companyManager.findThreshold(companyId));
        }

        ptUser = userRepository.save(ptUser);
        for (Long roleId : userBody.getRoles()) {
            //添加role信息
            userRoleManager.add(ptUser.getId(), roleId);
        }
        return ptUser;
    }


    public PtUser update(UserBody userBody) {
        Date now = CommonUtil.getNow();
        PtUser ptUser = userRepository.findOne(userBody.getId());
        BeanUtil.copyProperties(userBody, ptUser, BeanUtil.CopyOptions.create().setIgnoreNullValue(true));
        ptUser.setUpdateTime(now);
        ptUser.setPassword(CommonUtil.password(userBody.getPassword()));
        ptUser = userRepository.save(ptUser);
        //删除role对应关系
        userRoleManager.deleteByUserId(userBody.getId());
        for (Long roleId : userBody.getRoles()) {
            //添加role信息
            userRoleManager.add(ptUser.getId(), roleId);
        }
        return ptUser;
    }

    public void delete(Long id) {
        PtUser ptUser = userRepository.findOne(id);
        //被删除
        ptUser.setState(STATE_DELETE);
        ptUser.setUpdateTime(CommonUtil.getNow());
        userRepository.save(ptUser);
    }

    /**
     * 监听公司添加事件，然后添加一个管理员
     *
     * @param companyAddEvent
     *         公司添加事件
     */
    @EventListener
    public void companyAddEvent(CompanyAddEvent companyAddEvent) {
        CompanyBody companyBody = (CompanyBody) companyAddEvent.getSource();
        //添加公司管理员
        addAdmin(companyBody.getAccount(), companyBody.getPassword(), companyBody.getRoleId(), companyBody
                .getId());
    }

    /**
     * 从Ec同步用户信息
     *
     * @param companyUserBean
     *         ec的user信息
     * @return 本地的user
     */
    public PtUser add(CompanyUserBean companyUserBean, Long companyId, boolean force) {
        PtUser ptUser = userRepository.findByEcUserId(companyUserBean.getUserId());
        if (ptUser != null && !force) {
            return ptUser;
        }
        if (ptUser == null) {
            ptUser = new PtUser();
            ptUser.setState(STATE_NORMAL);
            ptUser.setCompanyId(companyId);
            ptUser.setPassword(CommonUtil.password(companyUserBean.getUserId() + ""));
        }
        ptUser.setCreateTime(CommonUtil.getNow());
        ptUser.setUpdateTime(CommonUtil.getNow());
        ptUser.setEcUserId(companyUserBean.getUserId());
        ptUser.setTitle(companyUserBean.getTitle());
        ptUser.setDepartmentId(departmentManager.findByEcDeptId(companyUserBean.getDeptId()).getId());
        ptUser.setName(companyUserBean.getUserName());
        ptUser.setAccount(companyManager.findOne(companyId).getPrefix() + "_" + companyUserBean.getAccount());
        ptUser.setMobile(companyUserBean.getAccount());
        // 设置阈值
        ptUser.setThreshold(departmentManager.findByEcDeptId(companyUserBean.getDeptId()).getThreshold());
        ptUser = userRepository.save(ptUser);

        return ptUser;
    }

    /**
     * 从Ec同步一批用户数据
     *
     * @param userBeanList
     *         ec用户集合
     * @return 本地集合
     */
    public List<PtUser> addUsers(List<CompanyUserBean> userBeanList, Long companyId, boolean force) {
        return userBeanList.stream().map(companyUserBean -> add(companyUserBean, companyId, force)).collect(Collectors
                .toList());
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

    public PtUser findByUserId(Long id) {
        return userRepository.findOne(id);
    }

    public PtUser findByEcUserId(Long ecUserId) {
        return userRepository.findByEcUserId(ecUserId);
    }

    public Long findEcUserId(Long id) {
        return findByUserId(id).getEcUserId();
    }

    public PtUser update(PtUser ptUser) {
        return userRepository.save(ptUser);
    }

    /**
     * 统计某个部门的人员数量
     *
     * @param departmentId
     *         部门id
     * @return 数量
     */
    public Integer countByDepartmentId(Long departmentId) {
        return userRepository.countByDepartmentIdAndState(departmentId, STATE_NORMAL);
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
    public List<PtUser> findByDeptIdAndState(Long deptId, Integer state) {
        return userRepository.findByDepartmentIdAndState(deptId, state);
    }

    public List<PtUser> findByCompanyIdAndState(Long companyId, Integer state) {
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
    public List<PtUser> findByDeptIdAndNameLike(Long deptId, String name) {
        if (StrUtil.isEmpty(name)) {
            return userRepository.findByDepartmentIdAndState(deptId, STATE_NORMAL);
        }
        return userRepository.findByDepartmentIdAndStateAndNameLike(deptId, STATE_NORMAL, "%" + name + "%");
    }

    public List<PtUser> findByCompanyIdAndNameLike(Long companyId, String name) {
        if (StrUtil.isEmpty(name)) {
            return userRepository.findByCompanyIdAndState(companyId, STATE_NORMAL);
        }
        return userRepository.findByCompanyIdAndStateAndNameLike(companyId, STATE_NORMAL, "%" + name + "%");
    }

    public List<PtUser> findAll() {
        return userRepository.findAll();
    }

    /**
     * 根据名字模糊查询
     *
     * @param name
     *         名字
     * @return 集合
     */
    public List<PtUser> findByNameLike(String name) {
        if (StrUtil.isEmpty(name)) {
            return userRepository.findByState(STATE_NORMAL);
        }
        return userRepository.findByStateAndNameLike(STATE_NORMAL, "%" + name + "%");
    }

    /**
     * 查询最大id值
     *
     * @return 结果
     */
    public Long findCompanyMaxUserId(Long companyId) {
        return userRepository.findCompanyMaxUserId(companyId);
    }

    /**
     * 查询id范围内的数据
     *
     * @param beginId
     *         开始id
     * @param endId
     *         结束id
     * @return 结果
     */
    public List<PtUser> findByIdBetweenAndCompanyId(Long beginId, Long endId, Long companyId) {
        return userRepository.findByIdBetweenAndCompanyId(beginId, endId, companyId);
    }

    /**
     * 修改用户推送的阈值
     *
     * @param userId
     *         用户id
     * @param threshold
     *         阈值
     */
    public void updateThresholdByUserId(Long userId, Integer threshold) {
        PtUser ptUser = userRepository.findOne(userId);
        ptUser.setThreshold(threshold);
        userRepository.save(ptUser);
    }

    /**
     * 用户状态异常，不可登录
     *
     * @param ptUser
     *         user
     * @return 是否异常状态
     */
    public boolean isErrorUser(PtUser ptUser) {
        return null == ptUser || ptUser.getState() == -1;
    }

    /**
     * 公司id查找管理员用户，创建最早的那个就是
     *
     * @param companyId
     *         companyId
     * @return PtUser
     */
    public PtUser findManagerByCompanyId(Long companyId) {
        return userRepository.findFirstByCompanyIdOrderByCreateTime(companyId);
    }

    /**
     * 判断是否是管理员
     *
     * @param userId
     *         userId
     * @return 结果
     */
    public boolean isManager(Long userId) {
        return userId.equals(findManagerByCompanyId(ShiroKit.getCurrentCompanyId()).getId());
    }
}
