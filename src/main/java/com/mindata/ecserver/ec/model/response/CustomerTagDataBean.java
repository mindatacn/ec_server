package com.mindata.ecserver.ec.model.response;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/16.
 */
public class CustomerTagDataBean {
    /**
     * 标签分组id
     */
    private Long groupId;
    /**
     * 标签分组名
     */
    private String groupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 类型
     */
    private Integer type;
    /**
     * tag的集合
     */
    private List<CustomerOneTagBean> list;

    public List<CustomerOneTagBean> getList() {
        return list;
    }

    public void setList(List<CustomerOneTagBean> list) {
        this.list = list;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
