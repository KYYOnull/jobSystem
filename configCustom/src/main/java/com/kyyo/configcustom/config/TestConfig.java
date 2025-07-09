package com.kyyo.configcustom.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


// 自动配置类 META-INF

@AutoConfiguration // TestConfig 被 Spring Boot 在启动时自动发现和加载
@EnableConfigurationProperties(value = TestProperties.class) // 读到此处 配置类TestProperties 注册到容器 并解析文件中相关配置
public class TestConfig {

    @Bean // 根据解析的属性 决定是否执行 testService  同名bean 注入到 Spring 容器
    @ConditionalOnProperty(value = {"test.enabled"}, havingValue = "true")
    public TestService testService(TestProperties testProperties) {

        return new TestService(testProperties);
    }
}