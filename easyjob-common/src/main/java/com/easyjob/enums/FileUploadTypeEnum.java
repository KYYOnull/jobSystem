package com.easyjob.enums;

public enum FileUploadTypeEnum {

    CATEGORY(0, 150, "分类图片"),
    CAROUSEL(1, 400, "轮播图"),
    SHARE_LARGE(2, 400, "分享大图"),
    SHARE_SMALL(3, 100, "分享小图");

    private Integer type;
    private Integer maxWidth;
    private String desc;

    FileUploadTypeEnum(Integer type, Integer maxWidth, String desc) {
        this.type = type;
        this.maxWidth = maxWidth;
        this.desc = desc;
    }

    public static FileUploadTypeEnum getByType(Integer type) {

        for (FileUploadTypeEnum it : FileUploadTypeEnum.values()) {
            if (it.getType().equals(type)) {
                return it;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public String getDesc() {
        return desc;
    }
}
