package com.mindata.ecserver.main.model;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 菜单+角色表
 */
@Entity
@Table(name = "pt_menu_role")
public class PtMenuRole extends BaseEntity {
    private int menuId;
    private int roleId;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
