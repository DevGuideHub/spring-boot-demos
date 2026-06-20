package io.github.cunyu1943.demolog4j2.controller;

import io.github.cunyu1943.demolog4j2.entity.LogInfo;
import io.github.cunyu1943.demolog4j2.service.Log4j2Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @description: Log4j2Controller 单元测试
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class Log4j2ControllerTest {

    @Mock
    private Log4j2Service log4j2Service;

    @InjectMocks
    private Log4j2Controller log4j2Controller;

    @BeforeEach
    void setUp() {
        log4j2Controller = new Log4j2Controller(log4j2Service);
    }

    @Test
    void testIndex_ShouldReturnHelloMessage() {
        String result = log4j2Controller.index();
        assertEquals("Hello Log4j2！", result);
    }

    @Test
    void testIndex_ShouldNotThrowException() {
        assertDoesNotThrow(() -> log4j2Controller.index());
    }

    @Test
    void testTestAllLevels_ShouldReturnSuccessMessage() {
        doNothing().when(log4j2Service).testAllLogLevels();

        String result = log4j2Controller.testAllLevels();

        assertEquals("Log4j2 日志测试完成！", result);
        verify(log4j2Service, times(1)).testAllLogLevels();
    }

    @Test
    void testTestParams_DefaultValues_ShouldWork() {
        doNothing().when(log4j2Service).testLogWithParams("张三", 25);

        String result = log4j2Controller.testParams("张三", 25);

        assertEquals("参数日志测试完成！", result);
        verify(log4j2Service, times(1)).testLogWithParams("张三", 25);
    }

    @Test
    void testTestParams_CustomValues_ShouldWork() {
        doNothing().when(log4j2Service).testLogWithParams("李四", 30);

        String result = log4j2Controller.testParams("李四", 30);

        assertEquals("参数日志测试完成！", result);
        verify(log4j2Service, times(1)).testLogWithParams("李四", 30);
    }

    @Test
    void testTestParams_EmptyName_ShouldWork() {
        doNothing().when(log4j2Service).testLogWithParams("", 25);

        String result = log4j2Controller.testParams("", 25);

        assertEquals("参数日志测试完成！", result);
    }

    @Test
    void testTestParams_NegativeAge_ShouldWork() {
        doNothing().when(log4j2Service).testLogWithParams("张三", -1);

        String result = log4j2Controller.testParams("张三", -1);

        assertEquals("参数日志测试完成！", result);
    }

    @Test
    void testTestException_ShouldReturnSuccessMessage() {
        doNothing().when(log4j2Service).testExceptionLog();

        String result = log4j2Controller.testException();

        assertEquals("异常日志测试完成！", result);
        verify(log4j2Service, times(1)).testExceptionLog();
    }

    @Test
    void testGetLogInfo_DefaultValues_ShouldReturnLogInfo() {
        LogInfo expected = LogInfo.builder()
                .level("INFO")
                .message("测试消息")
                .build();
        when(log4j2Service.getLogInfo("INFO", "测试消息")).thenReturn(expected);

        LogInfo result = log4j2Controller.getLogInfo("INFO", "测试消息");

        assertNotNull(result);
        assertEquals("INFO", result.getLevel());
        verify(log4j2Service, times(1)).getLogInfo("INFO", "测试消息");
    }

    @Test
    void testGetLogInfo_CustomValues_ShouldReturnLogInfo() {
        LogInfo expected = LogInfo.builder()
                .level("DEBUG")
                .message("自定义消息")
                .build();
        when(log4j2Service.getLogInfo("DEBUG", "自定义消息")).thenReturn(expected);

        LogInfo result = log4j2Controller.getLogInfo("DEBUG", "自定义消息");

        assertNotNull(result);
        assertEquals("DEBUG", result.getLevel());
        assertEquals("自定义消息", result.getMessage());
    }

    @Test
    void testGetLogInfo_NullLevel_ShouldHandle() {
        LogInfo expected = LogInfo.builder()
                .level("UNKNOWN")
                .message("测试消息")
                .build();
        when(log4j2Service.getLogInfo(null, "测试消息")).thenReturn(expected);

        LogInfo result = log4j2Controller.getLogInfo(null, "测试消息");

        assertNotNull(result);
    }

    @Test
    void testTestNested_ShouldReturnSuccessMessage() {
        doNothing().when(log4j2Service).testNestedLog();

        String result = log4j2Controller.testNested();

        assertEquals("嵌套日志测试完成！", result);
        verify(log4j2Service, times(1)).testNestedLog();
    }

    @Test
    void testControllerInstantiation() {
        assertNotNull(log4j2Controller);
    }

    @Test
    void testControllerWithService() {
        assertNotNull(log4j2Controller);
        assertTrue(log4j2Controller.getClass().getDeclaredFields().length > 0);
    }
}