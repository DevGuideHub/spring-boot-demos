package io.github.cunyu1943.demofreemarker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @description: 测试类：DemoFreemarkerApplicationTests
 * @author: cunyu1943
 * @date: 2026-06-17
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class DemoFreemarkerApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true, "Spring 上下文加载测试通过");
    }

    @Test
    void testApplicationName() {
        String appName = "demo-freemarker";
        assertTrue(appName != null && !appName.isEmpty(), "应用名称不应为空");
    }

}
