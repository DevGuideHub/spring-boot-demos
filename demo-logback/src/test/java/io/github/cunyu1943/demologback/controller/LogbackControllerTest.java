package io.github.cunyu1943.demologback.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: LogbackController 单元测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-17
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class LogbackControllerTest {

    private LogbackController logbackController;

    @BeforeEach
    void setUp() {
        logbackController = new LogbackController();
    }

    @Test
    void testIndex_ShouldReturnHelloMessage() {
        String result = logbackController.index();
        assertEquals("Hello Logback！", result);
    }

    @Test
    void testLog_ShouldReturnLogMessage() {
        String result = logbackController.testLog();
        assertEquals("Logback 日志测试完成！", result);
    }

    @Test
    void testLog_ShouldNotThrowException() {
        assertDoesNotThrow(() -> logbackController.testLog());
    }

    @Test
    void testIndex_ShouldNotThrowException() {
        assertDoesNotThrow(() -> logbackController.index());
    }

    @Test
    void testControllerInstantiation() {
        assertNotNull(logbackController);
    }
}
