package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@Entity
@Table(name = "pt_customer_group", indexes = {@Index(name = "user_id", columnList =
        "userId")})
public class PtCustomerGroup extends BaseEntity {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 分组ID
     */
    private String groupId;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 分组排序
     */
    private Integer sort;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


}
