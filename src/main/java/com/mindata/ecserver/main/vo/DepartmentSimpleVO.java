package com.mindata.ecserver.main.vo;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
public class DepartmentSimpleVO {
    private Integer id;
    private String name;
    private String leaders;
    private Integer count;

    public DepartmentSimpleVO() {
    }

    public DepartmentSimpleVO(Integer id, String name, String leaders, Integer count) {
        this.id = id;
        this.name = name;
        this.leaders = leaders;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DepartmentSimpleVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", leaders='" + leaders + '\'' +
                ", count=" + count +
                '}';
    }
}
