package com.easyjob.enums;

public enum CategoryTypeEnum {

    QUESTION(0, "八股文分类"),
    EXAM(1, "考题分类"),
    QUESTION_EXAM(2, "八股和考题分类");

    private final Integer type;

    CategoryTypeEnum(Integer type, String desc) {
        this.type = type;
    }

    public static CategoryTypeEnum getByType(Integer type) {

        for (CategoryTypeEnum e : CategoryTypeEnum.values()) {
            if (e.getType().equals(type)) return e;
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

}
