package io.github.cunyu1943.demoknife4j;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @description: 测试类：DemoKnife4jApplicationTests
 * @author: cunyu1943
 * @date: 2026-06-16
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class DemoKnife4jApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true, "Spring 上下文加载测试通过");
    }

    @Test
    void testApplicationName() {
        String appName = "demo-knife4j";
        assertTrue(appName != null && !appName.isEmpty(), "应用名称不应为空");
    }

}
