package io.github.cunyu1943.demoslf4jlog4j2.controller;

import io.github.cunyu1943.demoslf4jlog4j2.dto.Result;
import io.github.cunyu1943.demoslf4jlog4j2.service.HelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @description: Hello控制器测试类
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("HelloController Hello控制器测试")
class HelloControllerTest {

    @Mock
    private HelloService helloService;

    @InjectMocks
    private HelloController helloController;

    @BeforeEach
    void setUp() {
        // 初始化已完成，由Mockito处理
    }

    @Test
    @DisplayName("hello - 返回Hello消息")
    void testHello() {
        Result<String> expectedResult = Result.success("Hello World！");
        when(helloService.getHello()).thenReturn(expectedResult);

        Result<String> result = helloController.hello();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("Hello World！", result.getData());
        verify(helloService, times(1)).getHello();
    }

    @Test
    @DisplayName("customHello - 正常名称")
    void testCustomHelloWithValidName() {
        String name = "Test";
        Result<String> expectedResult = Result.success("Hello, Test！");
        when(helloService.getCustomMessage(name)).thenReturn(expectedResult);

        Result<String> result = helloController.customHello(name);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("Hello, Test！", result.getData());
        verify(helloService, times(1)).getCustomMessage(name);
    }

    @Test
    @DisplayName("customHello - 空名称")
    void testCustomHelloWithEmptyName() {
        String name = "";
        Result<String> expectedResult = Result.fail("名称不能为空");
        when(helloService.getCustomMessage(name)).thenReturn(expectedResult);

        Result<String> result = helloController.customHello(name);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("名称不能为空", result.getRespMsg());
        verify(helloService, times(1)).getCustomMessage(name);
    }

    @Test
    @DisplayName("customHello - null名称")
    void testCustomHelloWithNullName() {
        Result<String> expectedResult = Result.fail("名称不能为空");
        when(helloService.getCustomMessage(null)).thenReturn(expectedResult);

        Result<String> result = helloController.customHello(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("名称不能为空", result.getRespMsg());
        verify(helloService, times(1)).getCustomMessage(null);
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