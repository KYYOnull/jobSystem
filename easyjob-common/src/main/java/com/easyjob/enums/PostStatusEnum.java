package com.easyjob.enums;

public enum PostStatusEnum {

    NO_POST(0, "待发布"),
    POST(1, "已发布");

    private final Integer status;
    private final String desc;

    PostStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static PostStatusEnum getByStatus(Integer status) {
        for (PostStatusEnum it : PostStatusEnum.values()) {
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
