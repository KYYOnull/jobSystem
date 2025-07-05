package com.easyjob.enums;

public enum AppUpdateTypeEnum {

    ALL(0, ".apk", "全更新"),
    WGT(1, ".wgt", "局部热更新");

    private final Integer type;
    private final String suffix;
    private final String desc;

    AppUpdateTypeEnum(Integer type, String suffix, String desc) {
        this.type = type;
        this.suffix = suffix;
        this.desc = desc;
    }

    public static AppUpdateTypeEnum getByType(Integer type) {

        for (AppUpdateTypeEnum it: AppUpdateTypeEnum.values()){
            if(it.getType().equals(type)){
                return it;
            }
        }

        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getDesc() {
        return desc;
    }
}
