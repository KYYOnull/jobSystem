package com.kyyo.configcustom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 读取application.yml配置

@Data
@ConfigurationProperties(prefix = "test")
public class TestProperties {

    private boolean enabled = true;

    private String name;

    private String desc;
}