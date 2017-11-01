package com.mindata.ecserver.ec.model.request;

import java.util.List;

/**
 * 批量创建客户请求
 */
public class CustomerCreateRequest {
    private Long optUserId;
    private Long followUserId;
    private Long groupId;
    private Object[] fieldNameMapping;
    private List<Object[]> fieldValueList;

    public Long getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(Long optUserId) {
        this.optUserId = optUserId;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Object[] getFieldNameMapping() {
        return fieldNameMapping;
    }

    public void setFieldNameMapping(Object[] fieldNameMapping) {
        this.fieldNameMapping = fieldNameMapping;
    }

    public List<Object[]> getFieldValueList() {
        return fieldValueList;
    }

    public void setFieldValueList(List<Object[]> fieldValueList) {
        this.fieldValueList = fieldValueList;
    }
}
