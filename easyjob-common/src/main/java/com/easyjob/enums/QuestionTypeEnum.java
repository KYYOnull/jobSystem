package com.easyjob.enums;

import com.easyjob.entity.po.QuestionInfo;

public enum QuestionTypeEnum {

    TRUE_FALSE(0, "判断题"),
    RADIO(1, "单选题"),
    CHECK_BOX(2, "多选题");

    private final Integer type;
    private final String desc;

    QuestionTypeEnum(int type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public static QuestionTypeEnum getByDesc(String desc){
        for(QuestionTypeEnum q : QuestionTypeEnum.values()){
            if(q.getDesc().equals(desc)){
                return q;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
