package com.easyjob.utils;


import com.easyjob.exception.BusinessException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 利用 apache poi 读 excel
public class ExcelUtils {

    private static final Logger logger= LoggerFactory.getLogger(ExcelUtils.class);

    public static List<List<String>> readExcel(MultipartFile file, String[] title, Integer startRowIdx) {

        InputStream in= null;
        Integer rowErr= 0; // 错误行定位

        try{

            in= file.getInputStream(); // file -> stream
            Workbook wb = WorkbookFactory.create(in);
            List<List<String>> dat=new ArrayList<>();
            Sheet sheet = wb.getSheetAt(0);
            if(sheet==null) throw new BusinessException("excel文件解析失败");

            for(int rowIdx=0; rowIdx<= sheet.getLastRowNum(); rowIdx++){
                rowErr= rowIdx;
                if(rowIdx< startRowIdx) continue; // 跳过
                List<String> rDat= new ArrayList<>();
                Row r = sheet.getRow(rowIdx); // 行内容
                if(r==null) continue;

                int maxColCnt= title.length; // 解析列的范围
                Boolean isRowAllEmpty= true;
                if(r.getLastCellNum()< maxColCnt) throw new BusinessException("请按照模板文件上传数据");

                for(int colIdx=0; colIdx<maxColCnt; colIdx++){
                    Cell c = r.getCell(colIdx);
                    String cDat = getCellDat(c);
                    rDat.add(cDat);
                    if(!StringTools.isEmpty(cDat)) isRowAllEmpty= false;
                }
                if(rowIdx == startRowIdx){
                    String uploadTitle = rDat.stream().collect(Collectors.joining("_"));
                    String templateTitle = Arrays.asList(title).stream().collect(Collectors.joining("_"));
                    if(!uploadTitle.equalsIgnoreCase(templateTitle)) throw new BusinessException("请按照模板文件上传数据");
                }

                if(isRowAllEmpty) continue;
//                if(rowIdx> startRowIdx) rDat.add(r);
            }
            return dat;
        }catch (BusinessException be){
            logger.error("文件解析错误 第 {} 行, 错误 {}", rowErr+1, be.getMessage());
            throw be;
        }catch (Exception e){
            logger.error("文件解析错误 第 {} 行, 错误 {}", rowErr+1, e.getMessage());
            throw new BusinessException("文件解析 第 "+ rowErr+1 +" 行错误 {}");
        }finally {
            if(in!=null){
                try {
                    in.close();
                }catch (IOException e){
                    logger.error(e.getMessage());
                }
            }
        }

    }

    private static String getCellDat(Cell c){

        if(c==null) return "";
        if(c.getCellType()==Cell.CELL_TYPE_NUMERIC){
            DecimalFormat deci = new DecimalFormat("#.##");
            return deci.format(c.getNumericCellValue());
        }else {
            String dat= c.getStringCellValue();
            return StringTools.isEmpty(dat)? "": dat.trim();
        }
    }
}
