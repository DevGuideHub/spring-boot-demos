package io.github.cunyu1943.demohello.controller;

import io.github.cunyu1943.demohello.dto.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: Hello World 控制器测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-04-16
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class HelloWorldControllerTest {

    private HelloWorldController helloWorldController;

    @BeforeEach
    void setUp() {
        helloWorldController = new HelloWorldController();
    }

    @Test
    void testIndex_ShouldReturnHelloMessage() {
        Result<String> result = helloWorldController.index();
        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertEquals("Hello World！", result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    void testIndex_ShouldNotThrowException() {
        assertDoesNotThrow(() -> helloWorldController.index());
    }

    @Test
    void testControllerInstantiation() {
        assertNotNull(helloWorldController);
    }
}