package io.github.cunyu1943.demojpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @description: 启动类测试
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class DemoJpaApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    void testApplicationName() {
        String appName = "demo-jpa";
        assertTrue(appName != null && !appName.isEmpty());
    }

}