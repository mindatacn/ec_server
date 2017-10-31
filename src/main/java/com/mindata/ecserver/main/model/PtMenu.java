package com.mindata.ecserver.main.model;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 菜单表
 */
@Entity
@Table(name = "pt_menu", indexes = {@Index(name = "parent_id", columnList =
        "parentId")})
public class PtMenu extends BaseEntity {

    /**
     * 菜单名
     */
    private String name;
    /**
     * 父菜单id（1级为0）
     */
    private int parentId;
    /**
     * 地址url("/contact")
     */
    private String url;
    /**
     * 权限字符串（"role:add","contact:push"）
     */
    private String permission;
    /**
     * '类型   0：目录   1：菜单   2：按钮'
     */
    private int type;
    /**
     * 菜单icon
     */
    private String icon;
    /**
     * 排序
     */
    private int orderNum;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
