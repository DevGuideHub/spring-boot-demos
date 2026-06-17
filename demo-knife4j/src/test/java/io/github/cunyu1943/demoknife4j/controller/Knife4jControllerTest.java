package io.github.cunyu1943.demoknife4j.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: Knife4j 控制器测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-16
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class Knife4jControllerTest {

    private Knife4jController knife4jController;

    @BeforeEach
    void setUp() {
        knife4jController = new Knife4jController();
    }

    @Test
    void testHello_ShouldReturnHelloMessage() {
        String result = knife4jController.hello();
        assertEquals("Hello World！", result);
    }

    @Test
    void testGreetingWithName_ShouldReturnGreeting() {
        String result = knife4jController.greeting("村雨");
        assertEquals("Hello, 村雨！", result);
    }

    @Test
    void testGreetingWithoutName_ShouldReturnDefaultGreeting() {
        String result = knife4jController.greeting(null);
        assertEquals("Hello, Guest！", result);
    }

    @Test
    void testGreetingWithEmptyString_ShouldReturnDefaultGreeting() {
        String result = knife4jController.greeting("");
        assertEquals("Hello, Guest！", result);
    }

    @Test
    void testControllerInstantiation() {
        assertNotNull(knife4jController);
    }
}
