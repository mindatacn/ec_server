package com.mindata.ecserver.ec.model.response;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
public class CompanyDataBean {
    private CompanyCorpBean corp;

    private List<CompanyDeptBean> depts;

    private List<CompanyUserBean> users;

    public CompanyCorpBean getCorp() {
        return corp;
    }

    public void setCorp(CompanyCorpBean corp) {
        this.corp = corp;
    }

    public List<CompanyDeptBean> getDepts() {
        return depts;
    }

    public void setDepts(List<CompanyDeptBean> depts) {
        this.depts = depts;
    }

    public List<CompanyUserBean> getUsers() {
        return users;
    }

    public void setUsers(List<CompanyUserBean> users) {
        this.users = users;
    }
}
