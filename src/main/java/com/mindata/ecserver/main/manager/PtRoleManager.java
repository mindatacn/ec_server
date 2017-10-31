package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.PtRole;
import com.mindata.ecserver.main.model.PtUserRole;
import com.mindata.ecserver.main.repository.PtRoleRepository;
import com.mindata.ecserver.main.repository.PtUserRoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class PtRoleManager {
    @Resource
    private PtRoleRepository ptRoleRepository;
    @Resource
    private PtUserRoleRepository ptUserRoleRepository;

    /**
     * 查询用户的所有role
     *
     * @param userId
     *         userId
     * @return 集合
     */
    public List<PtRole> findByUserId(int userId) {
        List<PtUserRole> userRoles = ptUserRoleRepository.findByUserId(userId);
        return userRoles.stream().map(userRole -> ptRoleRepository.findOne(userRole.getRoleId())).collect(Collectors
                .toList());
    }

    public Integer findIdByName(String name) {
        return ptRoleRepository.findByName(name).getId();
    }
}
