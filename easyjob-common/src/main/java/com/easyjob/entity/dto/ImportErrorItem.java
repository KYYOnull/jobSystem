package com.easyjob.entity.dto;

import java.util.List;

public class ImportErrorItem {

    private Integer rowNum; // 第 row 行 出现了哪些错误
    private List<String> errorItemLst;

    public List<String> getErrorItemLst() {
        return errorItemLst;
    }

    public void setErrorItemLst(List<String> errorItemLst) {
        this.errorItemLst = errorItemLst;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }
}
