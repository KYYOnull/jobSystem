package com.easyjob.service;

import com.easyjob.entity.dto.StatisticsDataDto;
import com.easyjob.entity.dto.StatisticsDataWeekDto;

import java.util.List;

public interface StatisticsDataService {

    List<StatisticsDataDto> getAllDta();

    StatisticsDataWeekDto getAppWeekData();

    StatisticsDataWeekDto getContentWeekData();
}
