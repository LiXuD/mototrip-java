package com.mototrip.common.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class SentinelConfig {

    @PostConstruct
    public void initSentinelRules() {
        initFlowRules();
        initDegradeRules();
        log.info("Sentinel rules initialized");
    }

    /**
     * Flow control rules - rate limiting
     * Matches NestJS ThrottlerModule: 100 requests per minute per IP
     */
    private void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();

        // Global API flow rule: 100 requests per second per origin (IP)
        FlowRule globalRule = new FlowRule();
        globalRule.setResource("global-api");
        globalRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        globalRule.setCount(100);
        globalRule.setLimitApp("default");
        globalRule.setStrategy(RuleConstant.STRATEGY_DIRECT);
        rules.add(globalRule);

        // Write operations: stricter limit (20 QPS)
        FlowRule writeRule = new FlowRule();
        writeRule.setResource("write-api");
        writeRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        writeRule.setCount(20);
        writeRule.setLimitApp("default");
        writeRule.setStrategy(RuleConstant.STRATEGY_DIRECT);
        rules.add(writeRule);

        // Auth operations: very strict (10 QPS) to prevent brute force
        FlowRule authRule = new FlowRule();
        authRule.setResource("auth-api");
        authRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        authRule.setCount(10);
        authRule.setLimitApp("default");
        authRule.setStrategy(RuleConstant.STRATEGY_DIRECT);
        rules.add(authRule);

        FlowRuleManager.loadRules(rules);
        log.info("Sentinel flow rules loaded: {} rules", rules.size());
    }

    /**
     * Circuit breaking rules - degrade when error rate is high
     */
    private void initDegradeRules() {
        List<DegradeRule> rules = new ArrayList<>();

        // Slow call ratio: trigger when >50% of calls take >3s
        DegradeRule slowCallRule = new DegradeRule();
        slowCallRule.setResource("global-api");
        slowCallRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        slowCallRule.setCount(3000); // 3 seconds
        slowCallRule.setSlowRatioThreshold(0.5);
        slowCallRule.setTimeWindow(30); // 30 seconds recovery
        slowCallRule.setMinRequestAmount(10);
        slowCallRule.setStatIntervalMs(10000);
        rules.add(slowCallRule);

        // Error ratio: trigger when >50% of calls fail
        DegradeRule errorRatioRule = new DegradeRule();
        errorRatioRule.setResource("global-api");
        errorRatioRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        errorRatioRule.setCount(0.5); // 50% error ratio
        errorRatioRule.setTimeWindow(30);
        errorRatioRule.setMinRequestAmount(10);
        errorRatioRule.setStatIntervalMs(10000);
        rules.add(errorRatioRule);

        // Exception count: trigger after 10 consecutive errors
        DegradeRule exceptionCountRule = new DegradeRule();
        exceptionCountRule.setResource("global-api");
        exceptionCountRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        exceptionCountRule.setCount(10);
        exceptionCountRule.setTimeWindow(60);
        exceptionCountRule.setMinRequestAmount(10);
        exceptionCountRule.setStatIntervalMs(10000);
        rules.add(exceptionCountRule);

        DegradeRuleManager.loadRules(rules);
        log.info("Sentinel degrade rules loaded: {} rules", rules.size());
    }
}
