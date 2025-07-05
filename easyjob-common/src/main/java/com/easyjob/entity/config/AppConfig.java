package com.easyjob.entity.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("d:/data/tmp")
    private String prjFolder;

    @Value("17324199443")
    private String superAdminPhone;

    public String getPrjFolder() {
        return prjFolder;
    }

    public String getSuperAdminPhone() {
        return superAdminPhone;
    }
}
