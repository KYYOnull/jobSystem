package com.kyyo.testneedconfig;

import com.kyyo.configcustom.config.TestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Resource
    private TestService myService;  // 引用 单例不会被自动加锁

    @RequestMapping("/print")
    public String print() {

        myService.print();
        return "已调用 TestService.print()";
    }
}
