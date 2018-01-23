package com.mindata.ecserver.main.vo;

/**
 * @author hanliqiang wrote on 2018/1/22
 */
public class CompanyVO {
    private Long id;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 联系人
     */
    private String contactPerson;
    /**
     * 账号
     */
    private String account;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 状态
     */
    private Integer buyStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    @Override
    public String toString() {
        return "CompanyVO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", account='" + account + '\'' +
                ", roleName='" + roleName + '\'' +
                ", buyStatus=" + buyStatus +
                '}';
    }
}
