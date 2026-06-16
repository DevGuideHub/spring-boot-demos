package io.github.cunyu1943.demoknife4j.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @description: Hello World 控制器测试
 * @author: cunyu1943
 * @date: 2026-06-16
 * @fileName: Knife4jControllerTest
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

class Knife4jControllerTest {

    private final Knife4jController knife4jController = new Knife4jController();

    @Test
    void testHello() {
        String result = knife4jController.hello();
        assertEquals("Hello World！", result);
    }

    @Test
    void testGreetingWithName() {
        String result = knife4jController.greeting("村雨");
        assertEquals("Hello, 村雨！", result);
    }

    @Test
    void testGreetingWithoutName() {
        String result = knife4jController.greeting(null);
        assertEquals("Hello, Guest！", result);
    }
}