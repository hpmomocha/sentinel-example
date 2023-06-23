package com.hpe.kevin.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.hpe.kevin.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class UserController {
    private static final String USER_RESOURCE_NAME = "user";

    @GetMapping("/user")
    // @SentinelResource 注解改善了代码的侵入性
    // 使用方法:
    // 1. 添加依赖 <artifactId>sentinel-annotation-aspectj</artifactId>
    // 2. 在配置类中添加bean SentinelResourceAspect
    @SentinelResource(
            value = USER_RESOURCE_NAME,
            fallback = "fallbackHandler4GetUser",
            blockHandler = "blockHandler4GetUser")
    public User getUser(String id) {
        Random random = new Random();
        int i = random.nextInt(10);
        if (i > 5) {
            int ex = 1/0;
        }
        return new User("Kevin888");
    }

    /**
     * 注意:
     *  1. 一定要用 public 修饰
     *  2. 返回值一定要和源方法保证一致, 且要包含源方法的参数, 并且参数顺序一致
     *  3. 可以在参数最后添加 BlockException 参数, 可以区分是哪类异常, 然后做出相应的处理
     *     BlockException 是抽象类, 实现类包括:
     *     a. AuthorityException
     *     b. DegradeException
     *     c. FlowException
     *     d. SystemBlockException
     * @param id
     * @param e
     * @return
     */
    public User blockHandler4GetUser(String id, BlockException e) {
        e.printStackTrace();
        return new User("流控!");
    }

    public User fallbackHandler4GetUser(String id, Throwable e) {
        e.printStackTrace();
        return new User("异常处理!");
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
        rule.setResource(USER_RESOURCE_NAME);
        // 设置流控规则 QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(2);
        rules.add(rule);
        // 加载流控规则
        FlowRuleManager.loadRules(rules);
    }
}
