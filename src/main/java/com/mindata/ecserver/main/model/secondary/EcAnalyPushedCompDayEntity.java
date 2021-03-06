package com.mindata.ecserver.main.model.secondary;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author wuweifeng wrote on 2017/10/24.
 */
@Entity
@Table(name = "ec_analy_pushed_comp_day")
public class EcAnalyPushedCompDayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date pushedDay;
    private Integer compNum;
    private Integer customerNum;
    private Integer pushedCompNum;
    private Integer pushedCustomerNum;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pushed_day")
    public Date getPushedDay() {
        return pushedDay;
    }

    public void setPushedDay(Date pushedDay) {
        this.pushedDay = pushedDay;
    }

    @Basic
    @Column(name = "comp_num")
    public Integer getCompNum() {
        return compNum;
    }

    public void setCompNum(Integer compNum) {
        this.compNum = compNum;
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
    @Column(name = "pushed_comp_num")
    public Integer getPushedCompNum() {
        return pushedCompNum;
    }

    public void setPushedCompNum(Integer pushedCompNum) {
        this.pushedCompNum = pushedCompNum;
    }

    @Basic
    @Column(name = "pushed_customer_num")
    public Integer getPushedCustomerNum() {
        return pushedCustomerNum;
    }

    public void setPushedCustomerNum(Integer pushedCustomerNum) {
        this.pushedCustomerNum = pushedCustomerNum;
    }


    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pushedDay != null ? pushedDay.hashCode() : 0);
        result = 31 * result + (compNum != null ? compNum.hashCode() : 0);
        result = 31 * result + (customerNum != null ? customerNum.hashCode() : 0);
        result = 31 * result + (pushedCompNum != null ? pushedCompNum.hashCode() : 0);
        result = 31 * result + (pushedCustomerNum != null ? pushedCustomerNum.hashCode() : 0);
        return result;
    }
}
