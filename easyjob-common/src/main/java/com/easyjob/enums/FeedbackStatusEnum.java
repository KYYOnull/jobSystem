package com.easyjob.enums;

public enum FeedbackStatusEnum {

    NOT_REPLY(0, ""),
    REPLY(1, "");

    private final Integer status;
    private final String desc;

    FeedbackStatusEnum(Integer status, String desc){
        this.status= status;
        this.desc = desc;
    }

    public static FeedbackStatusEnum getByStatus(Integer status){
        for(FeedbackStatusEnum it: FeedbackStatusEnum.values()){
            if(it.status.equals(status)){
                return it;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
