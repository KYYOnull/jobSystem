package com.easyjob.enums;

public enum StatisticsDataTypeEnum {

    APP_DOWNLOAD(0, "App下载"),
    REGISTER_USER(1, "注册用户"),
    QUESTION_INFO(2, "八股文"),
    EXAM(3, "考题"),
    SHARE(4, "分享"),
    FEEDBACK(5, "反馈");

    private final Integer type;
    private final String desc;

    StatisticsDataTypeEnum(Integer type, String description) {
        this.type = type;
        this.desc = description;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static StatisticsDataTypeEnum getByType(Integer type) {
        for (StatisticsDataTypeEnum item : StatisticsDataTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}
