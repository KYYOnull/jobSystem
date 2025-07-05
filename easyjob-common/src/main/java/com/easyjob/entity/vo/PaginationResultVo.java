package com.easyjob.entity.vo;

import java.util.ArrayList;
import java.util.List;;

public class PaginationResultVo<T> {

//    前端看到的字段
    private Integer totalCnt;
    private Integer pageSize;
    private Integer pageNo;
    private Integer pageTotal;
    private List<T> lst = new ArrayList<>();

    public PaginationResultVo(Integer totalCnt, Integer pageSize, Integer pageNo, List<T> lst) {
        this.totalCnt = totalCnt;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.lst = lst;
    }

    public PaginationResultVo(Integer totalCnt, Integer pageSize, Integer pageNo, Integer pageTotal, List<T> lst) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        this.totalCnt = totalCnt;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.lst = lst;
    }

    public PaginationResultVo(List<T> lst) {
        this.lst = lst;
    }

    public PaginationResultVo() {
        // Default constructor
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getLst() {
        return lst;
    }

    public void setLst(List<T> lst) {
        this.lst = lst;
    }
}
