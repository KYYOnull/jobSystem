package com.kyyo.testneedconfig;

import com.kyyo.configcustom.config.TestService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestNeedConfigApplication {

    @Resource
    private TestService myService;

    public static void main(String[] args) {

        // .run 开始扫描 @Component、@Configuration、@Bean
        var context = SpringApplication.run(TestNeedConfigApplication.class, args);

        TestService service = context.getBean(TestService.class);
        service.print();

    }

    @PostConstruct // 所有依赖准备好
    public void run() {
        System.out.println("after context init");
        myService.print();
    } // 之后 Bean 放到容器 Tomcat 启动

}
