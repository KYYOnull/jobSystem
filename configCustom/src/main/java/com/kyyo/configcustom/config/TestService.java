package com.kyyo.configcustom.config;

// 别的模块调用

public class TestService {

    // 注入 MyProperties
    private final TestProperties props;

    public TestService(TestProperties testProperties) {

        this.props = testProperties; // 依赖倒置

        System.out.println("公共模块自动配置：" + testProperties);
    }

    public void print() {
        System.out.println("custom.name=" + props.getName());
        System.out.println("custom.desc=" + props.getDesc());
    }
}