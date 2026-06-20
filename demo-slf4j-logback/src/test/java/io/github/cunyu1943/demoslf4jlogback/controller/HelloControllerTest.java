package io.github.cunyu1943.demoslf4jlogback.controller;

import io.github.cunyu1943.demoslf4jlogback.dto.Result;
import io.github.cunyu1943.demoslf4jlogback.service.service.HelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @description: Hello控制器测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("HelloController 用户控制器测试")
class HelloControllerTest {

    /**
     * Hello服务mock
     */
    @Mock
    private HelloService helloService;

    /**
     * Hello控制器
     */
    @InjectMocks
    private HelloController helloController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("hello - 返回Hello消息")
    void testHello() {
        Result<String> expectedResult = Result.success("Hello World！");
        when(helloService.getHello()).thenReturn(expectedResult);

        Result<String> result = helloController.hello();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertEquals("Hello World！", result.getData());
        assertNotNull(result.getTimestamp());
        verify(helloService, times(1)).getHello();
    }

    @Test
    @DisplayName("hello - 服务返回失败")
    void testHelloWithServiceFailure() {
        Result<String> expectedResult = Result.fail("服务异常");
        when(helloService.getHello()).thenReturn(expectedResult);

        Result<String> result = helloController.hello();

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("服务异常", result.getRespMsg());
        verify(helloService, times(1)).getHello();
    }

    @Test
    @DisplayName("customHello - 返回自定义消息")
    void testCustomHello() {
        Result<String> expectedResult = Result.success("Hello, 测试！");
        when(helloService.getCustomMessage("测试")).thenReturn(expectedResult);

        Result<String> result = helloController.customHello("测试");

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertEquals("Hello, 测试！", result.getData());
        verify(helloService, times(1)).getCustomMessage("测试");
    }

    @Test
    @DisplayName("customHello - 空名称")
    void testCustomHelloWithEmptyName() {
        Result<String> expectedResult = Result.fail("名称不能为空");
        when(helloService.getCustomMessage("")).thenReturn(expectedResult);

        Result<String> result = helloController.customHello("");

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("名称不能为空", result.getRespMsg());
        verify(helloService, times(1)).getCustomMessage("");
    }

    @Test
    @DisplayName("testError - 测试错误日志")
    void testTestError() {
        Result<String> expectedResult = Result.success("错误日志测试完成");
        when(helloService.testErrorLog()).thenReturn(expectedResult);

        Result<String> result = helloController.testError();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("错误日志测试完成", result.getData());
        verify(helloService, times(1)).testErrorLog();
    }

}