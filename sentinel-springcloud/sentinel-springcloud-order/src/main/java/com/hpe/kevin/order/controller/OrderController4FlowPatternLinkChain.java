package com.hpe.kevin.order.controller;

import com.hpe.kevin.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 链路流控
 * 访问 /test1, 流控 /test2
 */
@RestController
@RequestMapping("/order4FlowLinkChain")
public class OrderController4FlowPatternLinkChain {
    @Autowired
    private OrderService orderService;

    @GetMapping("/test1")
    public String test1() {
        return orderService.getUser();
    }

    @GetMapping("/test2")
    public String test2() {
        return orderService.getUser();
    }
}
