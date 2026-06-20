package io.github.cunyu1943.demoslf4jlogback.service.impl;

import io.github.cunyu1943.demoslf4jlogback.dto.Result;
import io.github.cunyu1943.demoslf4jlogback.service.service.HelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: Hello服务实现测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("HelloService 用户服务测试")
class HelloServiceImplTest {

    /**
     * Hello服务实现
     */
    private HelloService helloService;

    @BeforeEach
    void setUp() {
        helloService = new HelloServiceImpl();
    }

    @Test
    @DisplayName("getHello - 返回Hello消息")
    void testGetHello() {
        Result<String> result = helloService.getHello();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertEquals("Hello World！", result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("getHello - 验证返回类型")
    void testGetHelloReturnType() {
        Result<String> result = helloService.getHello();
        assertTrue(result.getData() instanceof String);
    }

    @Test
    @DisplayName("getCustomMessage - 正常名称")
    void testGetCustomMessage() {
        Result<String> result = helloService.getCustomMessage("测试");

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertEquals("Hello, 测试！", result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("getCustomMessage - 空名称")
    void testGetCustomMessageWithEmptyName() {
        Result<String> result = helloService.getCustomMessage("");

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("名称不能为空", result.getRespMsg());
        assertNull(result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("getCustomMessage - null名称")
    void testGetCustomMessageWithNullName() {
        Result<String> result = helloService.getCustomMessage(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("名称不能为空", result.getRespMsg());
        assertNull(result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("testErrorLog - 测试错误日志")
    void testTestErrorLog() {
        Result<String> result = helloService.testErrorLog();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertEquals("错误日志测试完成", result.getData());
        assertNotNull(result.getTimestamp());
    }

}