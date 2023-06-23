package com.hpe.kevin.order.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hpe.kevin.order.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    @SentinelResource(value = "/getUser", blockHandler = "blockHandler4GetUser")
    public String getUser() {
        return "查询用户";
    }

    public String blockHandler4GetUser(BlockException e) {
        return "流控用户";
    }
}
