package com.mindata.ecserver.main.vo;

import java.util.List;
import java.util.Map;

/**
 * 联系人撰取分组功能VO
 *
 * @author wuweifeng wrote on 2017/12/1.
 */
public class ContactGroupVO {
    private Long totalCount;
    private List<Map<String, Object>> list;

    public ContactGroupVO(Long totalCount, List<Map<String, Object>> list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ContactGroupVO{" +
                "totalCount=" + totalCount +
                ", list=" + list +
                '}';
    }
}
