package io.github.cunyu1943.demohello.controller;

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
        String result = helloWorldController.index();
        assertEquals("Hello World！", result);
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
