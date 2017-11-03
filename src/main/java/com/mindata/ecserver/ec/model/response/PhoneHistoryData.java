package com.mindata.ecserver.ec.model.response;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/3.
 */
public class PhoneHistoryData {
    /**
     * 每页固定50
     */
    private int pageSize;
    /**
     * 第几页
     */
    private int pageNo;
    /**
     * 总数量
     */
    private int total;
    /**
     * 总页数
     */
    private int maxPageNo;
    /**
     * 开始行
     */
    private int startRow;
    private List<PhoneHistoryDataBean> result;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMaxPageNo() {
        return maxPageNo;
    }

    public void setMaxPageNo(int maxPageNo) {
        this.maxPageNo = maxPageNo;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public List<PhoneHistoryDataBean> getResult() {
        return result;
    }

    public void setResult(List<PhoneHistoryDataBean> result) {
        this.result = result;
    }
}
