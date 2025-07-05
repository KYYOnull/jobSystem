package com.easyjob.enums;

public enum DateTimePatternEnum {

    _YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    _YYYY_MM_DD("yyyy-MM-dd"),
    _YYYY_MM("YYYYMM");

    private final String pattern;

    DateTimePatternEnum(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
