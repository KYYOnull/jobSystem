package com.easyjob.enums;

public enum PageSize {

    SIZE15(15),
    SIZE20(20),
    SIZE30(30),
    SIZE40(40),
    SIZE50(50);

    private final Integer size;

    private PageSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return this.size;
    }
}

// PageSize pageSize = PageSize.SIZE20; // 构造函数调用 设置size为20
// System.out.println("Page size value: " + pageSize.getSize()); // 20
