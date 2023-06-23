package com.hpe.kevin.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/add")
    public String add() {
        System.out.println("下单成功");
        return "Hello World!";
    }

    @GetMapping("/flow")
    public String flow() {
        return "正常访问";
    }

    // 并发线程数流控
    // 与 QPS 流控的区别在于:
    // QPS 流控是单位时间内请求的个数不能超过设定的阈值
    // 并发线程数是 单位时间内处理中的请求个数不能超过设定的阈值
    @GetMapping("/flowByThread")
    public String flowByThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return "正常访问";
    }
}
