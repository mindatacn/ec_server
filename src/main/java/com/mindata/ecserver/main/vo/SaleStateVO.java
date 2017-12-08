package com.mindata.ecserver.main.vo;

import java.util.List;

/**
 * 线索的聚合沟通情况，销售情况
 *
 * @author wuweifeng wrote on 2017/12/8.
 */
public class SaleStateVO {
    /**
     * 线索总量
     */
    private List<Long> totalContact;
    /**
     * 新增线索量
     */
    private List<Long> addedContact;
    /**
     * 有意向线索量
     */
    private List<Long> intentedContact;
    /**
     * 接通量
     */
    private List<Long> connectedContact;
    /**
     * 有效沟通量
     */
    private List<Long> validedContact;
    /**
     * 成单量
     */
    private List<Long> saledContact;

    public List<Long> getTotalContact() {
        return totalContact;
    }

    public void setTotalContact(List<Long> totalContact) {
        this.totalContact = totalContact;
    }

    public List<Long> getAddedContact() {
        return addedContact;
    }

    public void setAddedContact(List<Long> addedContact) {
        this.addedContact = addedContact;
    }

    public List<Long> getIntentedContact() {
        return intentedContact;
    }

    public void setIntentedContact(List<Long> intentedContact) {
        this.intentedContact = intentedContact;
    }

    public List<Long> getConnectedContact() {
        return connectedContact;
    }

    public void setConnectedContact(List<Long> connectedContact) {
        this.connectedContact = connectedContact;
    }

    public List<Long> getValidedContact() {
        return validedContact;
    }

    public void setValidedContact(List<Long> validedContact) {
        this.validedContact = validedContact;
    }

    public List<Long> getSaledContact() {
        return saledContact;
    }

    public void setSaledContact(List<Long> saledContact) {
        this.saledContact = saledContact;
    }

    @Override
    public String toString() {
        return "SaleStateVO{" +
                "totalContact=" + totalContact +
                ", addedContact=" + addedContact +
                ", intentedContact=" + intentedContact +
                ", connectedContact=" + connectedContact +
                ", validedContact=" + validedContact +
                ", saledContact=" + saledContact +
                '}';
    }
}
