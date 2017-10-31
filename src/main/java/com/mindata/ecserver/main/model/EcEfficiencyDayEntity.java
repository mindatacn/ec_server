package com.mindata.ecserver.main.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/24.
 */
@Entity
@Table(name = "ec_efficiency_day")
public class EcEfficiencyDayEntity {
    private int id;
    private Date date;
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
    private Integer callMemberNum;
    private Integer memberNum;
    private Integer memberSaleNum;
    private Integer memberServiceNum;
    private Integer callMemberSaleNum;
    private Integer callMemberServiceNum;
    private Integer callSaleNum;
    private Integer callSaleDuration;
    private Integer callSaleAnswerNum;
    private Integer callSaleAnswerGt1Lt29;
    private Integer callSaleAnswerGt30Lt59;
    private Integer callSaleAnswerGt60;
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
    @Column(name = "call_member_num")
    public Integer getCallMemberNum() {
        return callMemberNum;
    }

    public void setCallMemberNum(Integer callMemberNum) {
        this.callMemberNum = callMemberNum;
    }

    @Basic
    @Column(name = "member_num")
    public Integer getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Integer memberNum) {
        this.memberNum = memberNum;
    }

    @Basic
    @Column(name = "member_sale_num")
    public Integer getMemberSaleNum() {
        return memberSaleNum;
    }

    public void setMemberSaleNum(Integer memberSaleNum) {
        this.memberSaleNum = memberSaleNum;
    }

    @Basic
    @Column(name = "member_service_num")
    public Integer getMemberServiceNum() {
        return memberServiceNum;
    }

    public void setMemberServiceNum(Integer memberServiceNum) {
        this.memberServiceNum = memberServiceNum;
    }

    @Basic
    @Column(name = "call_member_sale_num")
    public Integer getCallMemberSaleNum() {
        return callMemberSaleNum;
    }

    public void setCallMemberSaleNum(Integer callMemberSaleNum) {
        this.callMemberSaleNum = callMemberSaleNum;
    }

    @Basic
    @Column(name = "call_member_service_num")
    public Integer getCallMemberServiceNum() {
        return callMemberServiceNum;
    }

    public void setCallMemberServiceNum(Integer callMemberServiceNum) {
        this.callMemberServiceNum = callMemberServiceNum;
    }

    @Basic
    @Column(name = "call_sale_num")
    public Integer getCallSaleNum() {
        return callSaleNum;
    }

    public void setCallSaleNum(Integer callSaleNum) {
        this.callSaleNum = callSaleNum;
    }

    @Basic
    @Column(name = "call_sale_duration")
    public Integer getCallSaleDuration() {
        return callSaleDuration;
    }

    public void setCallSaleDuration(Integer callSaleDuration) {
        this.callSaleDuration = callSaleDuration;
    }

    @Basic
    @Column(name = "call_sale_answer_num")
    public Integer getCallSaleAnswerNum() {
        return callSaleAnswerNum;
    }

    public void setCallSaleAnswerNum(Integer callSaleAnswerNum) {
        this.callSaleAnswerNum = callSaleAnswerNum;
    }

    @Basic
    @Column(name = "call_sale_answer_gt1_lt29")
    public Integer getCallSaleAnswerGt1Lt29() {
        return callSaleAnswerGt1Lt29;
    }

    public void setCallSaleAnswerGt1Lt29(Integer callSaleAnswerGt1Lt29) {
        this.callSaleAnswerGt1Lt29 = callSaleAnswerGt1Lt29;
    }

    @Basic
    @Column(name = "call_sale_answer_gt30_lt59")
    public Integer getCallSaleAnswerGt30Lt59() {
        return callSaleAnswerGt30Lt59;
    }

    public void setCallSaleAnswerGt30Lt59(Integer callSaleAnswerGt30Lt59) {
        this.callSaleAnswerGt30Lt59 = callSaleAnswerGt30Lt59;
    }

    @Basic
    @Column(name = "call_sale_answer_gt60")
    public Integer getCallSaleAnswerGt60() {
        return callSaleAnswerGt60;
    }

    public void setCallSaleAnswerGt60(Integer callSaleAnswerGt60) {
        this.callSaleAnswerGt60 = callSaleAnswerGt60;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcEfficiencyDayEntity that = (EcEfficiencyDayEntity) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (customerNum != null ? !customerNum.equals(that.customerNum) : that.customerNum != null) return false;
        if (relateNum != null ? !relateNum.equals(that.relateNum) : that.relateNum != null) return false;
        if (callNum != null ? !callNum.equals(that.callNum) : that.callNum != null) return false;
        if (callDuration != null ? !callDuration.equals(that.callDuration) : that.callDuration != null) return false;
        if (qqNum != null ? !qqNum.equals(that.qqNum) : that.qqNum != null) return false;
        if (ecNum != null ? !ecNum.equals(that.ecNum) : that.ecNum != null) return false;
        if (messageNum != null ? !messageNum.equals(that.messageNum) : that.messageNum != null) return false;
        if (emailNum != null ? !emailNum.equals(that.emailNum) : that.emailNum != null) return false;
        if (visitNum != null ? !visitNum.equals(that.visitNum) : that.visitNum != null) return false;
        if (followNum != null ? !followNum.equals(that.followNum) : that.followNum != null) return false;
        if (totalNum != null ? !totalNum.equals(that.totalNum) : that.totalNum != null) return false;
        if (callAnswerNum != null ? !callAnswerNum.equals(that.callAnswerNum) : that.callAnswerNum != null)
            return false;
        if (callAnswerGt1Lt29 != null ? !callAnswerGt1Lt29.equals(that.callAnswerGt1Lt29) : that.callAnswerGt1Lt29 !=
                null)
            return false;
        if (callAnswerGt30Lt59 != null ? !callAnswerGt30Lt59.equals(that.callAnswerGt30Lt59) : that
                .callAnswerGt30Lt59 != null)
            return false;
        if (callAnswerGt60 != null ? !callAnswerGt60.equals(that.callAnswerGt60) : that.callAnswerGt60 != null)
            return false;
        if (callMemberNum != null ? !callMemberNum.equals(that.callMemberNum) : that.callMemberNum != null)
            return false;
        if (memberNum != null ? !memberNum.equals(that.memberNum) : that.memberNum != null) return false;
        if (memberSaleNum != null ? !memberSaleNum.equals(that.memberSaleNum) : that.memberSaleNum != null)
            return false;
        if (memberServiceNum != null ? !memberServiceNum.equals(that.memberServiceNum) : that.memberServiceNum != null)
            return false;
        if (callMemberSaleNum != null ? !callMemberSaleNum.equals(that.callMemberSaleNum) : that.callMemberSaleNum !=
                null)
            return false;
        if (callMemberServiceNum != null ? !callMemberServiceNum.equals(that.callMemberServiceNum) : that
                .callMemberServiceNum != null)
            return false;
        if (callSaleNum != null ? !callSaleNum.equals(that.callSaleNum) : that.callSaleNum != null) return false;
        if (callSaleDuration != null ? !callSaleDuration.equals(that.callSaleDuration) : that.callSaleDuration != null)
            return false;
        if (callSaleAnswerNum != null ? !callSaleAnswerNum.equals(that.callSaleAnswerNum) : that.callSaleAnswerNum !=
                null)
            return false;
        if (callSaleAnswerGt1Lt29 != null ? !callSaleAnswerGt1Lt29.equals(that.callSaleAnswerGt1Lt29) : that
                .callSaleAnswerGt1Lt29 != null)
            return false;
        if (callSaleAnswerGt30Lt59 != null ? !callSaleAnswerGt30Lt59.equals(that.callSaleAnswerGt30Lt59) : that
                .callSaleAnswerGt30Lt59 != null)
            return false;
        if (callSaleAnswerGt60 != null ? !callSaleAnswerGt60.equals(that.callSaleAnswerGt60) : that
                .callSaleAnswerGt60 != null)
            return false;
        if (maidaCallNum != null ? !maidaCallNum.equals(that.maidaCallNum) : that.maidaCallNum != null) return false;
        if (maidaCallAnswerNum != null ? !maidaCallAnswerNum.equals(that.maidaCallAnswerNum) : that
                .maidaCallAnswerNum != null)
            return false;
        if (maidaCallAnswerGt1Lt29 != null ? !maidaCallAnswerGt1Lt29.equals(that.maidaCallAnswerGt1Lt29) : that
                .maidaCallAnswerGt1Lt29 != null)
            return false;
        if (maidaCallAnswerGt30Lt59 != null ? !maidaCallAnswerGt30Lt59.equals(that.maidaCallAnswerGt30Lt59) : that
                .maidaCallAnswerGt30Lt59 != null)
            return false;
        if (maidaCallAnswerGt60 != null ? !maidaCallAnswerGt60.equals(that.maidaCallAnswerGt60) : that
                .maidaCallAnswerGt60 != null)
            return false;
        if (maidaTagedNum != null ? !maidaTagedNum.equals(that.maidaTagedNum) : that.maidaTagedNum != null)
            return false;
        if (maidaNoTagNum != null ? !maidaNoTagNum.equals(that.maidaNoTagNum) : that.maidaNoTagNum != null)
            return false;
        if (maidaInterestedNum != null ? !maidaInterestedNum.equals(that.maidaInterestedNum) : that
                .maidaInterestedNum != null)
            return false;
        if (maidaNoInterestedNum != null ? !maidaNoInterestedNum.equals(that.maidaNoInterestedNum) : that
                .maidaNoInterestedNum != null)
            return false;
        if (maidaGiveUpNum != null ? !maidaGiveUpNum.equals(that.maidaGiveUpNum) : that.maidaGiveUpNum != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
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
        result = 31 * result + (callMemberNum != null ? callMemberNum.hashCode() : 0);
        result = 31 * result + (memberNum != null ? memberNum.hashCode() : 0);
        result = 31 * result + (memberSaleNum != null ? memberSaleNum.hashCode() : 0);
        result = 31 * result + (memberServiceNum != null ? memberServiceNum.hashCode() : 0);
        result = 31 * result + (callMemberSaleNum != null ? callMemberSaleNum.hashCode() : 0);
        result = 31 * result + (callMemberServiceNum != null ? callMemberServiceNum.hashCode() : 0);
        result = 31 * result + (callSaleNum != null ? callSaleNum.hashCode() : 0);
        result = 31 * result + (callSaleDuration != null ? callSaleDuration.hashCode() : 0);
        result = 31 * result + (callSaleAnswerNum != null ? callSaleAnswerNum.hashCode() : 0);
        result = 31 * result + (callSaleAnswerGt1Lt29 != null ? callSaleAnswerGt1Lt29.hashCode() : 0);
        result = 31 * result + (callSaleAnswerGt30Lt59 != null ? callSaleAnswerGt30Lt59.hashCode() : 0);
        result = 31 * result + (callSaleAnswerGt60 != null ? callSaleAnswerGt60.hashCode() : 0);
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
