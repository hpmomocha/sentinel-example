package com.hpe.kevin.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关联流控
 * 访问 /add, 流控 /get
 */
@RestController
@RequestMapping("/orderFlowRelative")
public class OrderController4FlowPatternRelative {

    @GetMapping("/add")
    public String add() {
        System.out.println("下单成功");
        return "生成订单";
    }

    @GetMapping("/get")
    public String get() {
        return "查询订单";
    }

}
