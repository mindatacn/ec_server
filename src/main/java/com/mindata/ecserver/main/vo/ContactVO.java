package com.mindata.ecserver.main.vo;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
public class ContactVO {
    private Long id;
    /**
     * 人名
     */
    private String name;
    /**
     * 公司名
     */
    private String company;
    /**
     * 行业
     */
    private String vocation;
    /**
     * 区域
     */
    private String province;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
