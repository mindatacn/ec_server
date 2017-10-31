package com.mindata.ecserver.main.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author wuweifeng wrote on 2017/10/24.
 */
@Entity
@Table(name = "ec_analy_vocation_day")
public class EcAnalyVocationDayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date pushDay;
    private Integer vocationTag;
    private Integer num;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "push_day")
    public Date getPushDay() {
        return pushDay;
    }

    public void setPushDay(Date pushDay) {
        this.pushDay = pushDay;
    }

    @Basic
    @Column(name = "vocation_tag")
    public Integer getVocationTag() {
        return vocationTag;
    }

    public void setVocationTag(Integer vocationTag) {
        this.vocationTag = vocationTag;
    }

    @Basic
    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pushDay != null ? pushDay.hashCode() : 0);
        result = 31 * result + (vocationTag != null ? vocationTag.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        return result;
    }
}
