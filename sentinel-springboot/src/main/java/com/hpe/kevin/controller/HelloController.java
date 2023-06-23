package com.hpe.kevin.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class HelloController {

    private static final String RESOURCE_NAME = "hello";

    // 流控
    @GetMapping("/hello")
    public String hello() {
        Entry entry = null;

        try {
            // sentinel 针对资源进行限制
            entry = SphU.entry(RESOURCE_NAME);
            String str = "hello world";
            log.info("======" + str + "======");
            return str;
        } catch (BlockException e) {
//            throw new RuntimeException(e);
            log.info("blocked!");
            return "被流控了!";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

    /**
     * 定义规则
     * Spring 的初始化方法
     */
    @PostConstruct // 相当于 xml 配置中的 init-method
    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        // 流控规则
        FlowRule rule = new FlowRule();
        // 设置受保护的资源(对哪个资源进行流控)
        rule.setResource(RESOURCE_NAME);
        // 设置流控规则 QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(2);
        rules.add(rule);
        // 加载流控规则
        FlowRuleManager.loadRules(rules);
    }
}
