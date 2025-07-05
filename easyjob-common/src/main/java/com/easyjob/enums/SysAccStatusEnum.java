package com.easyjob.enums;

public enum SysAccStatusEnum {

    ENABLE(1, "可用账户"),
    DISABLE(0, "冻结账户");

    private final Integer status;
    private final String desc;

    SysAccStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}
