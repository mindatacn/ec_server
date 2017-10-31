package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtUserRole;
import com.mindata.ecserver.main.repository.secondary.PtUserRoleRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/10/31.
 */
@Service
public class PtUserRoleManager {
    @Resource
    private PtUserRoleRepository ptUserRoleRepository;

    /**
     * 添加一个role和user的对应关系
     */
    public PtUserRole add(Integer userId, Integer roleId) {
        PtUserRole ptUserRole = ptUserRoleRepository.findByUserIdAndRoleId(userId, roleId);
        if (ptUserRole != null) {
            return ptUserRole;
        }
        ptUserRole = new PtUserRole();
        ptUserRole.setRoleId(roleId);
        ptUserRole.setUserId(userId);
        ptUserRole.setCreateTime(CommonUtil.getNow());
        ptUserRole.setUpdateTime(CommonUtil.getNow());
        return ptUserRoleRepository.save(ptUserRole);
    }
}
