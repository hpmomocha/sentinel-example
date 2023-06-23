package com.hpe.kevin.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.hpe.kevin.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DegradeController {
    private static final String DEGRADE_RESOURCE_NAME = "degrade";

    @GetMapping("degrade")
    @SentinelResource(value = DEGRADE_RESOURCE_NAME, entryType = EntryType.IN, blockHandler = "blockHandler4Fb")
    public User degrade(String id) throws InterruptedException {
        throw new RuntimeException("异常");
    }

    public User blockHandler4Fb(String id, BlockException e) {
        return new User("熔断降级");
    }

    @PostConstruct
    public void initDegradeRule() {
        List<DegradeRule> degradeRules = new ArrayList<>();

        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(DEGRADE_RESOURCE_NAME);
        // 设置触发熔断规则: 异常数
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 触发熔断异常数: 2
        degradeRule.setCount(2);
        // 触发熔断最小请求数: 2
        degradeRule.setMinRequestAmount(2);
        // 统计时常 单位: ms 默认为1ms 页面上没有对应项目可以设置
        // 时间太短不好测试, 所以此处设置为1分钟
        degradeRule.setStatIntervalMs(60 * 1000);
        // 结合以上3个设置, 如果在1分钟内, 执行了2次(以上)请求, 且有2次请求发生异常, 就会触发熔断

        // 触发熔断后持续时长, 即在熔断发生后的10秒内, 再次请求对应的接口就会直接调用降级方法(blockHandler指定的方法)
        // 经过10秒后(半开状态), 会恢复接口请求调用(调用源方法)
        // 但是如果第一次请求就发生异常, 则直接再次进入熔断(不会根据设置的熔断条件进行判断)
        degradeRule.setTimeWindow(10);

        degradeRules.add(degradeRule);

        DegradeRuleManager.loadRules(degradeRules);
    }
}
