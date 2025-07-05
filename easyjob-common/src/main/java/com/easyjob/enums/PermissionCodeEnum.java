package com.easyjob.enums;

public enum PermissionCodeEnum {

    APP_FEEDBACK_LIST("", "问题反馈"),
    APP_FEEDBACK_REPLY("", ""),

    APP_USER_LIST("", "app用户"),
    APP_USER_EDIT("", "app用户编辑"),

    APP_USER_DEVICE("", "app用户设备");

    private String code;
    private String desc;

    private PermissionCodeEnum(String code, String desc) {}
}
