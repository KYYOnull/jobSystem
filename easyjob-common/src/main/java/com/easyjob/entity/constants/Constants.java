package com.easyjob.entity.constants;

import java.util.Arrays;

public class Constants {

    public static final String CHECK_CODE_KEY = "check_code_key";

    public static final String SESSION_KEY = "session_key";

    public static final Integer LEN_8 = 8;

    public static final Integer LEN_20 = 20;

    public static final Integer LEN_50 = 50;

    public static final Integer LEN_150 = 150;

    public static final Integer DEFAULT_ROOT_MENUID= 0;

    public static final String[] EXCEL_TITLE_QUESTION= new String[]{ // 问答
            "标题", "分类名称", "难度", "问题描述", "答案分析"
    };

    public static final String[] EXCEL_TITLE_EXAM_QUESTION= new String[]{ // 单选多选判断
            "标题", "分类名称", "难度", "问题类型", "问题选项", "问题答案", "问题描述", "答案分析"
    };

    public static final String TABLE_NAME_QUESTION_INFO = "question_info";
    public static final String TABLE_NAME_SHARE_INFO = "share_info";

    public static final String TRUE_STR = "正确";
    public static final String FALSE_STR = "错误";

    public static final String ZERO_STR = "0";
    public static final String ONE_STR = "1";

    public static final String[] LETTERS= new String[]{
            "A", "B", "C", "D", "E", "F"
    };

    public static final String APP_UPDATE_FOLDER = "/app/";

    public static void main(String[] args) {

        System.out.println(Arrays.binarySearch(new String[]{
            "A",
                "B",
//                        "C",
//                        "D",
                        "E", "F"
        },
//                "A"
//                "G"
                "D"
        ));
    }
}
