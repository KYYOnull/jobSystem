package com.easyjob.enums;

public enum FeedbackSendTypeEnum {

    CLIENT(0, "访客"),
    ADMIN(1, "管理员");

    private final Integer status;
    private final String desc;

    FeedbackSendTypeEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static FeedbackSendTypeEnum getByStatus(Integer status) {
        for(FeedbackSendTypeEnum it: FeedbackSendTypeEnum.values()) {
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
