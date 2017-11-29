package com.mindata.ecserver.ec.model.request;

/**
 * @author wuweifeng wrote on 2017/11/28.
 */
public class UserTrajector {
    private String id;
    private Long crmId;
    private String contactTime;
    /**
     * 操作人id
     */
    private Long userId;
    /**
     * 跟进内容
     */
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }

    public String getContactTime() {
        return contactTime;
    }

    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
