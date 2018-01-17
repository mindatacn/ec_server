package com.mindata.ecserver.main.requestbody;

import java.util.List;

/**
 * @author wuweifeng wrote on 2018/1/17.
 */
public class RoleMenuDto {
    private Long roleId;
    private List<Long> menuIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }

    @Override
    public String toString() {
        return "RoleMenuDto{" +
                "roleId=" + roleId +
                ", menuIds=" + menuIds +
                '}';
    }
}
