package com.easyjob.enums;

public enum AppUpdateStatusEnum {

    INIT(0, "未发布"),
    GRAYSCALE(1, "灰度发布"),
    ALL(2, "已发布");

    private final Integer status;
    private final String desc;

    AppUpdateStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static AppUpdateStatusEnum getByStatus(Integer status) {
        for (AppUpdateStatusEnum it : AppUpdateStatusEnum.values()) {
            if (it.getStatus().equals(status)) {
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
