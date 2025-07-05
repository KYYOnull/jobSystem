package com.easyjob.controller;

import com.easyjob.entity.dto.StatisticsDataDto;
import com.easyjob.entity.po.AppUserInfo;
import com.easyjob.entity.query.CategoryQuery;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.service.AppUserInfoService;
import com.easyjob.service.StatisticsDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController("indexController")
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Resource
    private StatisticsDataService statSvc;

    @RequestMapping("/getAllData")
    public ResponseVo<List<StatisticsDataDto>> getAllData(CategoryQuery cateQry){

        return getSuccessResponseVo(statSvc.getAllDta());
    }

}
