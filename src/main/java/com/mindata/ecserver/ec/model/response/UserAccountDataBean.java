package com.mindata.ecserver.ec.model.response;

/**
 * @author wuweifeng wrote on 2017/11/6.
 */
public class UserAccountDataBean {
    private Long f_user_id;
    private String f_mobile;

    public String getF_mobile() {
        return f_mobile;
    }

    public void setF_mobile(String f_mobile) {
        this.f_mobile = f_mobile;
    }

    public Long getF_user_id() {
        return f_user_id;
    }

    public void setF_user_id(Long f_user_id) {
        this.f_user_id = f_user_id;
    }
}
