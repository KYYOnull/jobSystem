package com.easyjob.controller;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.entity.config.AppConfig;
import com.easyjob.entity.constants.Constants;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.enums.DateTimePatternEnum;
import com.easyjob.enums.FileUploadTypeEnum;
import com.easyjob.enums.ImportTemplateTypeEnum;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import com.easyjob.utils.DateUtils;
import com.easyjob.utils.ScaleFilter;
import com.easyjob.utils.StringTools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

@RestController("fileController")
@RequestMapping("/file")
public class FileController extends BaseController{

    private static final Logger logger= LoggerFactory.getLogger(FileController.class);

    @Resource
    private AppConfig appConf;

    @RequestMapping("/uploadFile")
    @GlobalInterceptor
    public ResponseVo<String> uploadFile(MultipartFile file, Integer type){

        FileUploadTypeEnum uploadEnum = FileUploadTypeEnum.getByType(type);
        String month= DateUtils.format(new Date(), DateTimePatternEnum._YYYY_MM.getPattern()); // "yyyy-MM-dd"
        String folderName = appConf.getPrjFolder()+ month;
        File folder = new File(folderName);
        if(!folder.exists()) folder.mkdirs();

        String dotSuffix = StringTools.getFileSuffix(file.getOriginalFilename());
        String fName = StringTools.getRandomString(Constants.LEN_20)+ dotSuffix;
        String fPth= month+ "/"+ fName;
        File f = new File(appConf.getPrjFolder() + fPth);
        try{
            file.transferTo(f);
            if(uploadEnum != null){
                ScaleFilter.createThumbNail(f, uploadEnum.getMaxWidth(), uploadEnum.getMaxWidth(), f);
            }

        } catch (IOException e){
            logger.error("文件上传失败", e.getMessage());
            throw new BusinessException("文件上传失败");
        }

        return getSuccessResponseVo(fPth);
    }

    @RequestMapping("/getImage/(imageFolder)/(imageName)")
    @GlobalInterceptor
    public void getImage(
            HttpServletResponse res,
            @PathVariable("imageFolder") String imgFolder,
            @PathVariable("imageName") String imgName){

        readImg(res, imgFolder, imgName);

    }

    private void readImg(HttpServletResponse res, String imgFolder, String imgName) {

        if(StringTools.isEmpty(imgFolder) || StringUtils.isBlank(imgName)) return;

        String imgSuffix = StringTools.getFileSuffix(imgName);
        String fPth = appConf.getPrjFolder()+ imgFolder+ "/"+ imgName;
        imgSuffix = imgSuffix.replace(".", "");
        String contType = "image/" + imgSuffix;
        res.setContentType(contType);
        res.setHeader("Cache-Control", "max-age=2592000"); // 3d

        readFile(res, fPth);
    }

    private void readFile(HttpServletResponse res, String fPth) {

//        if(!StringTools.pathIsOk(fPth)) return;


    }

    @RequestMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse res, HttpServletRequest req, Integer type){

        ImportTemplateTypeEnum templateTypeEnum= ImportTemplateTypeEnum.getByType(type);
        if(null == templateTypeEnum) throw new BusinessException(ResponseCodeEnum.CODE_600); // 没有该模板
        OutputStream out= null;
        InputStream in= null;

        try{
            String fileName = templateTypeEnum.getTemplateName();
            res.setContentType("application/x-msdownload; charset=utf-8");
            if(req.getHeader("User-Agent").toLowerCase().contains("ms")){
                fileName= URLEncoder.encode(fileName, "UTF-8");
            }else {
                fileName= new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }

            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            // 读 resources 下
            ClassPathResource clzPthRsc = new ClassPathResource(templateTypeEnum.getTemplatePath());
            in= clzPthRsc.getInputStream();
            byte[] bDat = new byte[1024];
            out= res.getOutputStream();
            int len = 0;
            while((len = in.read(bDat))!=-1){ // 读流
                out.write(bDat,0, len);
            }
            out.flush();
        } catch (Exception e) {

            logger.error("读模板异常: ", e);
        } finally {
            if(null != out){
                try {
                    out.close();
                }catch (IOException e){
                    logger.error("out流关闭IO异常", e);
                }
            }
            if(null != in){
                try {
                    in.close();
                }catch (IOException e){
                    logger.error("in流关闭异常: ", e);
                }
            }
        }

    }
}
