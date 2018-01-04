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
    private List<Integer> totalContact;
    private List<Double> totalContactPercent;
    /**
     * 新增线索量
     */
    private List<Integer> addedContact;
    private List<Double> addedContactPercent;
    /**
     * 有意向线索量
     */
    private List<Integer> intentedContact;
    private List<Double> intentedContactPercent;
    /**
     * 接通量(大于0的)
     */
    private List<Integer> connectedContact;
    /**
     * 接通量(包含0的)
     */
    private List<Integer> totalConnectedContact;
    private List<Integer> connected30Contact;
    private List<Integer> connected60Contact;
    private List<Integer> connected120Contact;
    private List<Integer> connected240Contact;
    private List<Double> connectedContactPercent;
    private List<Double> connected30ContactPercent;
    private List<Double> connected60ContactPercent;
    private List<Double> connected120ContactPercent;
    private List<Double> connected240ContactPercent;
    /**
     * 有效沟通量
     */
    private List<Integer> validedContact;
    private List<Double> validedContactPercent;
    /**
     * 成单量
     */
    private List<Integer> saledContact;
    private List<Double> saledContactPercent;

    public List<Integer> getTotalConnectedContact() {
        return totalConnectedContact;
    }

    public void setTotalConnectedContact(List<Integer> totalConnectedContact) {
        this.totalConnectedContact = totalConnectedContact;
    }

    public List<Integer> getTotalContact() {
        return totalContact;
    }

    public void setTotalContact(List<Integer> totalContact) {
        this.totalContact = totalContact;
    }

    public List<Integer> getAddedContact() {
        return addedContact;
    }

    public List<Double> getTotalContactPercent() {
        return totalContactPercent;
    }

    public void setTotalContactPercent(List<Double> totalContactPercent) {
        this.totalContactPercent = totalContactPercent;
    }

    public List<Double> getConnected30ContactPercent() {
        return connected30ContactPercent;
    }

    public void setConnected30ContactPercent(List<Double> connected30ContactPercent) {
        this.connected30ContactPercent = connected30ContactPercent;
    }

    public List<Double> getConnected60ContactPercent() {
        return connected60ContactPercent;
    }

    public void setConnected60ContactPercent(List<Double> connected60ContactPercent) {
        this.connected60ContactPercent = connected60ContactPercent;
    }

    public List<Double> getConnected120ContactPercent() {
        return connected120ContactPercent;
    }

    public void setConnected120ContactPercent(List<Double> connected120ContactPercent) {
        this.connected120ContactPercent = connected120ContactPercent;
    }

    public List<Double> getConnected240ContactPercent() {
        return connected240ContactPercent;
    }

    public void setConnected240ContactPercent(List<Double> connected240ContactPercent) {
        this.connected240ContactPercent = connected240ContactPercent;
    }

    public List<Double> getAddedContactPercent() {
        return addedContactPercent;
    }

    public void setAddedContactPercent(List<Double> addedContactPercent) {
        this.addedContactPercent = addedContactPercent;
    }

    public List<Integer> getConnected30Contact() {
        return connected30Contact;
    }

    public void setConnected30Contact(List<Integer> connected30Contact) {
        this.connected30Contact = connected30Contact;
    }

    public List<Integer> getConnected60Contact() {
        return connected60Contact;
    }

    public void setConnected60Contact(List<Integer> connected60Contact) {
        this.connected60Contact = connected60Contact;
    }

    public List<Integer> getConnected120Contact() {
        return connected120Contact;
    }

    public void setConnected120Contact(List<Integer> connected120Contact) {
        this.connected120Contact = connected120Contact;
    }

    public List<Integer> getConnected240Contact() {
        return connected240Contact;
    }

    public void setConnected240Contact(List<Integer> connected240Contact) {
        this.connected240Contact = connected240Contact;
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

    public void setAddedContact(List<Integer> addedContact) {
        this.addedContact = addedContact;
    }

    public List<Integer> getIntentedContact() {
        return intentedContact;
    }

    public void setIntentedContact(List<Integer> intentedContact) {
        this.intentedContact = intentedContact;
    }

    public List<Integer> getConnectedContact() {
        return connectedContact;
    }

    public void setConnectedContact(List<Integer> connectedContact) {
        this.connectedContact = connectedContact;
    }

    public List<Integer> getValidedContact() {
        return validedContact;
    }

    public void setValidedContact(List<Integer> validedContact) {
        this.validedContact = validedContact;
    }

    public List<Integer> getSaledContact() {
        return saledContact;
    }

    public void setSaledContact(List<Integer> saledContact) {
        this.saledContact = saledContact;
    }

    @Override
    public String toString() {
        return "SaleStateVO{" +
                "totalContact=" + totalContact +
                ", totalContactPercent=" + totalContactPercent +
                ", addedContact=" + addedContact +
                ", addedContactPercent=" + addedContactPercent +
                ", intentedContact=" + intentedContact +
                ", intentedContactPercent=" + intentedContactPercent +
                ", connectedContact=" + connectedContact +
                ", totalConnectedContact=" + totalConnectedContact +
                ", connected30Contact=" + connected30Contact +
                ", connected60Contact=" + connected60Contact +
                ", connected120Contact=" + connected120Contact +
                ", connected240Contact=" + connected240Contact +
                ", connectedContactPercent=" + connectedContactPercent +
                ", connected30ContactPercent=" + connected30ContactPercent +
                ", connected60ContactPercent=" + connected60ContactPercent +
                ", connected120ContactPercent=" + connected120ContactPercent +
                ", connected240ContactPercent=" + connected240ContactPercent +
                ", validedContact=" + validedContact +
                ", validedContactPercent=" + validedContactPercent +
                ", saledContact=" + saledContact +
                ", saledContactPercent=" + saledContactPercent +
                '}';
    }
}
