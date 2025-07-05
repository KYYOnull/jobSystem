package com.easyjob.entity.dto;

import java.util.List;


public class StatisticsDataDto {

    private String statisticsName; // app下载
    private Integer cnt; // 使用数
    private Integer preCnt; // 昨日新增
    private List<Integer> listData; //

    public String getStatisticsName() {
        return statisticsName;
    }

    public void setStatisticsName(String statisticsName) {
        this.statisticsName = statisticsName;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getPreCnt() {
        return preCnt;
    }

    public void setPreCnt(Integer preCnt) {
        this.preCnt = preCnt;
    }

    public List<Integer> getListData() {
        return listData;
    }

    public void setListData(List<Integer> listData) {
        this.listData = listData;
    }
}
