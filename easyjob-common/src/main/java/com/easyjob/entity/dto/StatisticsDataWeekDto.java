package com.easyjob.entity.dto;

import java.util.List;

public class StatisticsDataWeekDto {

    private List<String> timelist;
    private List<StatisticsDataDto> itDataList;

    public List<String> getDatalist() {
        return timelist;
    }

    public void setDatalist(List<String> datalist) {
        this.timelist = datalist;
    }

    public List<StatisticsDataDto> getItDataList() {
        return itDataList;
    }

    public void setItDataList(List<StatisticsDataDto> itDataList) {
        this.itDataList = itDataList;
    }
}
