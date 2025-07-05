package com.easyjob.service.impl;

import com.easyjob.entity.dto.StatisticsDataDto;
import com.easyjob.entity.dto.StatisticsDataWeekDto;
import com.easyjob.entity.po.*;
import com.easyjob.entity.query.*;
import com.easyjob.enums.DateTimePatternEnum;
import com.easyjob.enums.StatisticsDataTypeEnum;
import com.easyjob.mappers.*;
import com.easyjob.service.StatisticsDataService;
import com.easyjob.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsDataServiceImp implements StatisticsDataService {

    @Resource // app下载量
    private AppDeviceMappers<AppDevice, AppDeviceQuery> deviceMapper;
    @Resource // app注册量
    private AppUserInfoMappers<AppUserInfo, AppUserInfoQuery> userInfoMapper;

    @Resource // 八股
    private QuestionInfoMappers<QuestionInfo, QuestionInfoQuery> qsInfoMapper;
    @Resource // 考题
    private ExamQuestionMappers<ExamQuestion, ExamQuestionQuery> exQsMapper;

    @Resource // 写作
    private ShareInfoMappers<ShareInfo, ShareInfoQuery> shareMapper;
    @Resource // 反馈
    private AppFeedbackMappers<AppFeedback, AppFeedbackQuery> feedbackMapper;


    @Override
    public List<StatisticsDataDto> getAllDta() {

        String preDate = DateUtils.format(DateUtils.getDayAgo(1), DateTimePatternEnum._YYYY_MM_DD.getPattern());
        ArrayList<StatisticsDataDto> dataLst = new ArrayList<>();

        for(StatisticsDataTypeEnum it: StatisticsDataTypeEnum.values()) {

            StatisticsDataDto dataDto = new StatisticsDataDto(); // 对每种类型分别查询数据
            dataDto.setStatisticsName(it.getDesc());

            switch (it){
                case APP_DOWNLOAD:
                    AppDeviceQuery deviceQry = new AppDeviceQuery();
                    dataDto.setCnt(this.deviceMapper.selectCount(deviceQry)); // 总记录数

                    deviceQry.setCreateTimeStart(preDate); // 7.4 - 7.5 today 7.5
                    deviceQry.setCreateTimeEnd(preDate);
                    dataDto.setPreCnt(this.deviceMapper.selectCount(deviceQry)); // 昨日记录数

//                    List<AppDevice> deviceData = this.deviceMapper.selectList(deviceQry);
                    break;
                case REGISTER_USER:
                    AppUserInfoQuery userInfoQry = new AppUserInfoQuery();
                    List<AppUserInfo> userInfoData = this.userInfoMapper.selectList(userInfoQry);
                    break;
                case QUESTION_INFO:
                    QuestionInfoQuery qsInfoQry = new QuestionInfoQuery();
                    List<QuestionInfo> qsInfoData = this.qsInfoMapper.selectList(qsInfoQry);
                    break;
                case EXAM:
                    ExamQuestionQuery exQsQry = new ExamQuestionQuery();
                    List<ExamQuestion> exQsData = this.exQsMapper.selectList(exQsQry);
                    break;
                case SHARE:
                    ShareInfoQuery shareQry = new ShareInfoQuery();
                    List<ShareInfo> shareData = this.shareMapper.selectList(shareQry);
                case FEEDBACK:
                    AppFeedbackQuery feedbackQry = new AppFeedbackQuery();
                    List<AppFeedback> feedbackData = this.feedbackMapper.selectList(feedbackQry);
            }
        }
//        return List.of();
        return null;
    }

    @Override
    public StatisticsDataWeekDto getAppWeekData() {


        return null;
    }

    @Override
    public StatisticsDataWeekDto getContentWeekData() {


        return null;
    }
}
