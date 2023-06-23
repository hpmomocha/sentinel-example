package com.hpe.kevin;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        // 整合 dashboard 控制台, 启动时需要增加 jvm 参数
        // -Dcsp.sentinel.dashboard.server=localhost:7001
        SpringApplication.run(StartApplication.class, args);
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
