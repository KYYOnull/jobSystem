package com.easyjob.enums;

public enum UserStatusEnum {

    DISABLE(0, ""),
    ENABLE(1, "");

    private final Integer status;
    private final String desc;

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static UserStatusEnum getByStatus(Integer status) {

        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.status.equals(status)) {
                return userStatusEnum;
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
