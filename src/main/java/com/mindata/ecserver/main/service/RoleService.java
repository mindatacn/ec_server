package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.RoleDeleteEvent;
import com.mindata.ecserver.main.event.RoleMenuChangeEvent;
import com.mindata.ecserver.main.manager.PtRoleManager;
import com.mindata.ecserver.main.manager.PtUserRoleManager;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.service.base.BaseService;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色管理
 *
 * @author wuweifeng wrote on 2018/1/18.
 */
@Service
public class RoleService extends BaseService {
    @Resource
    private PtRoleManager ptRoleManager;
    @Resource
    private PtUserRoleManager ptUserRoleManager;

    /**
     * 新增一个role。只有超级管理员和公司管理员才有该权限
     *
     * @param name
     *         name
     * @param sign
     *         sign
     * @return PtRole
     */
    public PtRole add(String name, String sign) {
        PtUser ptUser = ShiroKit.getCurrentUser();
        Long companyId = ptUser.getCompanyId();
        PtRole ptRole = new PtRole();
        ptRole.setName(name);
        ptRole.setSign(sign);
        ptRole.setCompanyId(companyId);

        return ptRoleManager.add(ptRole);
    }

    public PtRole update(Long id, String name, String sign) {
        PtRole ptRole = ptRoleManager.findByRoleId(id);
        ptRole.setName(name);
        ptRole.setSign(sign);

        return ptRoleManager.update(ptRole);
    }

    public boolean delete(Long id) {
        //如果公司内还有人是该角色，就不让删除
        if (ptUserRoleManager.findByRoleId(id).size() > 0) {
            return false;
        }
        ptRoleManager.delete(id);
        //发布菜单事件
        eventPublisher.publishEvent(new RoleMenuChangeEvent(CollectionUtil.newArrayList(id)));
        eventPublisher.publishEvent(new RoleDeleteEvent(id));
        return true;
    }

}
