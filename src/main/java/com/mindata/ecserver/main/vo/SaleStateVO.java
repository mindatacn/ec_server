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
    private List<Double> totalContactPercent;
    /**
     * 新增线索量
     */
    private List<Long> addedContact;
    private List<Double> addedContactPercent;
    /**
     * 有意向线索量
     */
    private List<Long> intentedContact;
    private List<Double> intentedContactPercent;
    /**
     * 接通量
     */
    private List<Long> connectedContact;
    private List<Double> connectedContactPercent;
    /**
     * 有效沟通量
     */
    private List<Long> validedContact;
    private List<Double> validedContactPercent;
    /**
     * 成单量
     */
    private List<Long> saledContact;
    private List<Double> saledContactPercent;

    public List<Long> getTotalContact() {
        return totalContact;
    }

    public void setTotalContact(List<Long> totalContact) {
        this.totalContact = totalContact;
    }

    public List<Long> getAddedContact() {
        return addedContact;
    }

    public List<Double> getTotalContactPercent() {
        return totalContactPercent;
    }

    public void setTotalContactPercent(List<Double> totalContactPercent) {
        this.totalContactPercent = totalContactPercent;
    }

    public List<Double> getAddedContactPercent() {
        return addedContactPercent;
    }

    public void setAddedContactPercent(List<Double> addedContactPercent) {
        this.addedContactPercent = addedContactPercent;
    }

    public List<Double> getIntentedContactPercent() {
        return intentedContactPercent;
    }

    public void setIntentedContactPercent(List<Double> intentedContactPercent) {
        this.intentedContactPercent = intentedContactPercent;
    }

    public List<Double> getConnectedContactPercent() {
        return connectedContactPercent;
    }

    public void setConnectedContactPercent(List<Double> connectedContactPercent) {
        this.connectedContactPercent = connectedContactPercent;
    }

    public List<Double> getValidedContactPercent() {
        return validedContactPercent;
    }

    public void setValidedContactPercent(List<Double> validedContactPercent) {
        this.validedContactPercent = validedContactPercent;
    }

    public List<Double> getSaledContactPercent() {
        return saledContactPercent;
    }

    public void setSaledContactPercent(List<Double> saledContactPercent) {
        this.saledContactPercent = saledContactPercent;
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
