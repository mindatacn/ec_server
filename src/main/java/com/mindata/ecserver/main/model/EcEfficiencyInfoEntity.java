package com.mindata.ecserver.main.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/24.
 */
@Entity
@Table(name = "ec_efficiency_info")
public class EcEfficiencyInfoEntity {
    private int id;
    private Date date;
    private String name;
    private String dept;
    private Integer customerNum;
    private Integer relateNum;
    private Integer callNum;
    private Integer callDuration;
    private Integer qqNum;
    private Integer ecNum;
    private Integer messageNum;
    private Integer emailNum;
    private Integer visitNum;
    private Integer followNum;
    private Integer totalNum;
    private Integer callAnswerNum;
    private Integer callAnswerGt1Lt29;
    private Integer callAnswerGt30Lt59;
    private Integer callAnswerGt60;
    private Integer maidaCallNum;
    private Integer maidaCallAnswerNum;
    private Integer maidaCallAnswerGt1Lt29;
    private Integer maidaCallAnswerGt30Lt59;
    private Integer maidaCallAnswerGt60;
    private Integer maidaTagedNum;
    private Integer maidaNoTagNum;
    private Integer maidaInterestedNum;
    private Integer maidaNoInterestedNum;
    private Integer maidaGiveUpNum;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "dept")
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Basic
    @Column(name = "customer_num")
    public Integer getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(Integer customerNum) {
        this.customerNum = customerNum;
    }

    @Basic
    @Column(name = "relate_num")
    public Integer getRelateNum() {
        return relateNum;
    }

    public void setRelateNum(Integer relateNum) {
        this.relateNum = relateNum;
    }

    @Basic
    @Column(name = "call_num")
    public Integer getCallNum() {
        return callNum;
    }

    public void setCallNum(Integer callNum) {
        this.callNum = callNum;
    }

    @Basic
    @Column(name = "call_duration")
    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    @Basic
    @Column(name = "qq_num")
    public Integer getQqNum() {
        return qqNum;
    }

    public void setQqNum(Integer qqNum) {
        this.qqNum = qqNum;
    }

    @Basic
    @Column(name = "ec_num")
    public Integer getEcNum() {
        return ecNum;
    }

    public void setEcNum(Integer ecNum) {
        this.ecNum = ecNum;
    }

    @Basic
    @Column(name = "message_num")
    public Integer getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(Integer messageNum) {
        this.messageNum = messageNum;
    }

    @Basic
    @Column(name = "email_num")
    public Integer getEmailNum() {
        return emailNum;
    }

    public void setEmailNum(Integer emailNum) {
        this.emailNum = emailNum;
    }

    @Basic
    @Column(name = "visit_num")
    public Integer getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
    }

    @Basic
    @Column(name = "follow_num")
    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    @Basic
    @Column(name = "total_num")
    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    @Basic
    @Column(name = "call_answer_num")
    public Integer getCallAnswerNum() {
        return callAnswerNum;
    }

    public void setCallAnswerNum(Integer callAnswerNum) {
        this.callAnswerNum = callAnswerNum;
    }

    @Basic
    @Column(name = "call_answer_gt1_lt29")
    public Integer getCallAnswerGt1Lt29() {
        return callAnswerGt1Lt29;
    }

    public void setCallAnswerGt1Lt29(Integer callAnswerGt1Lt29) {
        this.callAnswerGt1Lt29 = callAnswerGt1Lt29;
    }

    @Basic
    @Column(name = "call_answer_gt30_lt59")
    public Integer getCallAnswerGt30Lt59() {
        return callAnswerGt30Lt59;
    }

    public void setCallAnswerGt30Lt59(Integer callAnswerGt30Lt59) {
        this.callAnswerGt30Lt59 = callAnswerGt30Lt59;
    }

    @Basic
    @Column(name = "call_answer_gt60")
    public Integer getCallAnswerGt60() {
        return callAnswerGt60;
    }

    public void setCallAnswerGt60(Integer callAnswerGt60) {
        this.callAnswerGt60 = callAnswerGt60;
    }

    @Basic
    @Column(name = "maida_call_num")
    public Integer getMaidaCallNum() {
        return maidaCallNum;
    }

    public void setMaidaCallNum(Integer maidaCallNum) {
        this.maidaCallNum = maidaCallNum;
    }

    @Basic
    @Column(name = "maida_call_answer_num")
    public Integer getMaidaCallAnswerNum() {
        return maidaCallAnswerNum;
    }

    public void setMaidaCallAnswerNum(Integer maidaCallAnswerNum) {
        this.maidaCallAnswerNum = maidaCallAnswerNum;
    }

    @Basic
    @Column(name = "maida_call_answer_gt1_lt29")
    public Integer getMaidaCallAnswerGt1Lt29() {
        return maidaCallAnswerGt1Lt29;
    }

    public void setMaidaCallAnswerGt1Lt29(Integer maidaCallAnswerGt1Lt29) {
        this.maidaCallAnswerGt1Lt29 = maidaCallAnswerGt1Lt29;
    }

    @Basic
    @Column(name = "maida_call_answer_gt30_lt59")
    public Integer getMaidaCallAnswerGt30Lt59() {
        return maidaCallAnswerGt30Lt59;
    }

    public void setMaidaCallAnswerGt30Lt59(Integer maidaCallAnswerGt30Lt59) {
        this.maidaCallAnswerGt30Lt59 = maidaCallAnswerGt30Lt59;
    }

    @Basic
    @Column(name = "maida_call_answer_gt60")
    public Integer getMaidaCallAnswerGt60() {
        return maidaCallAnswerGt60;
    }

    public void setMaidaCallAnswerGt60(Integer maidaCallAnswerGt60) {
        this.maidaCallAnswerGt60 = maidaCallAnswerGt60;
    }

    @Basic
    @Column(name = "maida_taged_num")
    public Integer getMaidaTagedNum() {
        return maidaTagedNum;
    }

    public void setMaidaTagedNum(Integer maidaTagedNum) {
        this.maidaTagedNum = maidaTagedNum;
    }

    @Basic
    @Column(name = "maida_no_tag_num")
    public Integer getMaidaNoTagNum() {
        return maidaNoTagNum;
    }

    public void setMaidaNoTagNum(Integer maidaNoTagNum) {
        this.maidaNoTagNum = maidaNoTagNum;
    }

    @Basic
    @Column(name = "maida_interested_num")
    public Integer getMaidaInterestedNum() {
        return maidaInterestedNum;
    }

    public void setMaidaInterestedNum(Integer maidaInterestedNum) {
        this.maidaInterestedNum = maidaInterestedNum;
    }

    @Basic
    @Column(name = "maida_no_interested_num")
    public Integer getMaidaNoInterestedNum() {
        return maidaNoInterestedNum;
    }

    public void setMaidaNoInterestedNum(Integer maidaNoInterestedNum) {
        this.maidaNoInterestedNum = maidaNoInterestedNum;
    }

    @Basic
    @Column(name = "maida_give_up_num")
    public Integer getMaidaGiveUpNum() {
        return maidaGiveUpNum;
    }

    public void setMaidaGiveUpNum(Integer maidaGiveUpNum) {
        this.maidaGiveUpNum = maidaGiveUpNum;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dept != null ? dept.hashCode() : 0);
        result = 31 * result + (customerNum != null ? customerNum.hashCode() : 0);
        result = 31 * result + (relateNum != null ? relateNum.hashCode() : 0);
        result = 31 * result + (callNum != null ? callNum.hashCode() : 0);
        result = 31 * result + (callDuration != null ? callDuration.hashCode() : 0);
        result = 31 * result + (qqNum != null ? qqNum.hashCode() : 0);
        result = 31 * result + (ecNum != null ? ecNum.hashCode() : 0);
        result = 31 * result + (messageNum != null ? messageNum.hashCode() : 0);
        result = 31 * result + (emailNum != null ? emailNum.hashCode() : 0);
        result = 31 * result + (visitNum != null ? visitNum.hashCode() : 0);
        result = 31 * result + (followNum != null ? followNum.hashCode() : 0);
        result = 31 * result + (totalNum != null ? totalNum.hashCode() : 0);
        result = 31 * result + (callAnswerNum != null ? callAnswerNum.hashCode() : 0);
        result = 31 * result + (callAnswerGt1Lt29 != null ? callAnswerGt1Lt29.hashCode() : 0);
        result = 31 * result + (callAnswerGt30Lt59 != null ? callAnswerGt30Lt59.hashCode() : 0);
        result = 31 * result + (callAnswerGt60 != null ? callAnswerGt60.hashCode() : 0);
        result = 31 * result + (maidaCallNum != null ? maidaCallNum.hashCode() : 0);
        result = 31 * result + (maidaCallAnswerNum != null ? maidaCallAnswerNum.hashCode() : 0);
        result = 31 * result + (maidaCallAnswerGt1Lt29 != null ? maidaCallAnswerGt1Lt29.hashCode() : 0);
        result = 31 * result + (maidaCallAnswerGt30Lt59 != null ? maidaCallAnswerGt30Lt59.hashCode() : 0);
        result = 31 * result + (maidaCallAnswerGt60 != null ? maidaCallAnswerGt60.hashCode() : 0);
        result = 31 * result + (maidaTagedNum != null ? maidaTagedNum.hashCode() : 0);
        result = 31 * result + (maidaNoTagNum != null ? maidaNoTagNum.hashCode() : 0);
        result = 31 * result + (maidaInterestedNum != null ? maidaInterestedNum.hashCode() : 0);
        result = 31 * result + (maidaNoInterestedNum != null ? maidaNoInterestedNum.hashCode() : 0);
        result = 31 * result + (maidaGiveUpNum != null ? maidaGiveUpNum.hashCode() : 0);
        return result;
    }
}
