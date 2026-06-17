package io.github.cunyu1943.demologback.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: LogbackService 单元测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-17
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class LogbackServiceTest {

    private LogbackService logbackService;

    @BeforeEach
    void setUp() {
        logbackService = new LogbackService();
    }

    @Test
    void testLog_ShouldNotThrowException() {
        assertDoesNotThrow(() -> logbackService.testLog());
    }

    @Test
    void testLogWithParams_ShouldNotThrowException() {
        assertDoesNotThrow(() -> logbackService.testLogWithParams("张三", 25));
    }

    @Test
    void testLogWithParams_ShouldHandleNullAndEdgeValues() {
        assertDoesNotThrow(() -> logbackService.testLogWithParams(null, 0));
        assertDoesNotThrow(() -> logbackService.testLogWithParams("", -1));
        assertDoesNotThrow(() -> logbackService.testLogWithParams("test", Integer.MAX_VALUE));
    }

    @Test
    void testExceptionLog_ShouldNotThrowException() {
        assertDoesNotThrow(() -> logbackService.testExceptionLog());
    }

    @Test
    void testServiceInstantiation() {
        assertNotNull(logbackService);
    }

    @Test
    void testServiceIsNotNull() {
        assertTrue(logbackService != null);
    }
}
