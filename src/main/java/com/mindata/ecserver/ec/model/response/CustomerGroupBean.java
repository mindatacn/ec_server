package com.mindata.ecserver.ec.model.response;

/**
 * 工客户库分组
 * @author hanliqiang wrote on 2017/11/15
 */
public class CustomerGroupBean {
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

    @Override
    public String toString() {
        return "CustomerGroupBean{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", sort=" + sort +
                '}';
    }
}
