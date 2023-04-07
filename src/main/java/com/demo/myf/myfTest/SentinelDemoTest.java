package com.demo.myf.myfTest;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SentinelDemoTest {
    // 配置规则.
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(8);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    private static void initDegradeRules() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setCount(2);
        rule.setTimeWindow(1);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }
    /**  * 异常捕获式限流  * try-with-resources 语句  */
    @Test
    public void t1() throws InterruptedException {
        initFlowRules();
        while (true) {
            Thread.sleep(100);
            try (Entry entry = SphU.entry("HelloWorld")) {
                // 被保护的逻辑
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
                }
        }
    }
    /**  * 异常捕获式限流  * try finally 语句  */
    @Test
    public void t1_1() throws InterruptedException {
        initFlowRules();
        // 配置规则.
        while (true) {
            Thread.sleep(100);
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                // 被保护的逻辑
                System.out.println("hello world");
                // 处理被流控的逻辑
                System.out.println("blocked!");
            } catch (BlockException e) {
                e.printStackTrace();
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }
    /**  * 条件判断式限流  */
    @Test
    public void t2() throws InterruptedException {
        // 配置规则.
        initFlowRules();
        while (true) {
            Thread.sleep(100);
            boolean entryOk = SphO.entry("HelloWorld");
            if (entryOk) {
                // 被保护的逻辑
                System.out.println("hello world");
            } else {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
            SphO.exit();
        }
    }
    /**  * 降级  */
    @Test  public void t4() {
        initDegradeRules();
        while (true) {
            Entry entry = null;
            try {
                Thread.sleep(100);
                entry = SphU.entry("HelloWorld");
                throw new RuntimeException("exception");
            } catch (BlockException e) {
                System.out.println("degrade");
            } catch (Exception e) {
                System.out.println("pass");
                Tracer.trace(e);
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }
}
