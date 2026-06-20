package io.github.cunyu1943.demolog4j2.service;

import io.github.cunyu1943.demolog4j2.entity.LogInfo;
import io.github.cunyu1943.demolog4j2.service.impl.Log4j2ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @description: Log4j2ServiceImpl 单元测试
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class Log4j2ServiceImplTest {

    private Log4j2ServiceImpl log4j2Service;

    @BeforeEach
    void setUp() {
        log4j2Service = new Log4j2ServiceImpl();
    }

    @Test
    void testTestAllLogLevels_ShouldNotThrowException() {
        assertDoesNotThrow(() -> log4j2Service.testAllLogLevels());
    }

    @Test
    void testLogWithParams_NormalCase_ShouldWork() {
        assertDoesNotThrow(() -> log4j2Service.testLogWithParams("张三", 25));
    }

    @Test
    void testLogWithParams_NullName_ShouldHandle() {
        assertDoesNotThrow(() -> log4j2Service.testLogWithParams(null, 25));
    }

    @Test
    void testLogWithParams_EmptyName_ShouldHandle() {
        assertDoesNotThrow(() -> log4j2Service.testLogWithParams("", 25));
    }

    @Test
    void testLogWithParams_NegativeAge_ShouldHandle() {
        assertDoesNotThrow(() -> log4j2Service.testLogWithParams("张三", -1));
    }

    @Test
    void testLogWithParams_ExcessiveAge_ShouldHandle() {
        assertDoesNotThrow(() -> log4j2Service.testLogWithParams("张三", 200));
    }

    @Test
    void testLogWithParams_ZeroAge_ShouldWork() {
        assertDoesNotThrow(() -> log4j2Service.testLogWithParams("张三", 0));
    }

    @Test
    void testLogWithParams_MaxAge_ShouldWork() {
        assertDoesNotThrow(() -> log4j2Service.testLogWithParams("张三", 150));
    }

    @Test
    void testExceptionLog_ShouldNotThrowException() {
        assertDoesNotThrow(() -> log4j2Service.testExceptionLog());
    }

    @Test
    void testGetLogInfo_NormalCase_ShouldReturnLogInfo() {
        LogInfo result = log4j2Service.getLogInfo("INFO", "测试消息");

        assertNotNull(result);
        assertEquals("INFO", result.getLevel());
        assertEquals("测试消息", result.getMessage());
        assertNotNull(result.getTimestamp());
        assertNotNull(result.getThreadName());
    }

    @Test
    void testGetLogInfo_NullLevel_ShouldHandle() {
        LogInfo result = log4j2Service.getLogInfo(null, "测试消息");

        assertNotNull(result);
        assertEquals("UNKNOWN", result.getLevel());
    }

    @Test
    void testGetLogInfo_NullMessage_ShouldHandle() {
        LogInfo result = log4j2Service.getLogInfo("DEBUG", null);

        assertNotNull(result);
        assertNull(result.getMessage());
    }

    @Test
    void testGetLogInfo_LowerCaseLevel_ShouldUpperCase() {
        LogInfo result = log4j2Service.getLogInfo("debug", "测试消息");

        assertNotNull(result);
        assertEquals("DEBUG", result.getLevel());
    }

    @Test
    void testGetLogInfo_EmptyLevel_ShouldHandle() {
        LogInfo result = log4j2Service.getLogInfo("", "测试消息");

        assertNotNull(result);
        assertEquals("UNKNOWN", result.getLevel());
    }

    @Test
    void testNestedLog_ShouldNotThrowException() {
        assertDoesNotThrow(() -> log4j2Service.testNestedLog());
    }

    @Test
    void testServiceInstantiation() {
        assertNotNull(log4j2Service);
    }

    @Test
    void testLogInfoBuilder_ShouldBuildCorrectly() {
        LogInfo logInfo = LogInfo.builder()
                .level("ERROR")
                .message("错误消息")
                .build();

        assertNotNull(logInfo);
        assertEquals("ERROR", logInfo.getLevel());
        assertEquals("错误消息", logInfo.getMessage());
    }

    @Test
    void testLogInfoNoArgsConstructor_ShouldWork() {
        LogInfo logInfo = new LogInfo();
        assertNotNull(logInfo);
    }

    @Test
    void testLogInfoAllArgsConstructor_ShouldWork() {
        LogInfo logInfo = new LogInfo("INFO", "消息", null, "main");
        assertNotNull(logInfo);
        assertEquals("INFO", logInfo.getLevel());
        assertEquals("消息", logInfo.getMessage());
        assertEquals("main", logInfo.getThreadName());
    }

    @Test
    void testLogInfoSetters_ShouldWork() {
        LogInfo logInfo = new LogInfo();
        logInfo.setLevel("WARN");
        logInfo.setMessage("警告消息");
        logInfo.setThreadName("test");

        assertEquals("WARN", logInfo.getLevel());
        assertEquals("警告消息", logInfo.getMessage());
        assertEquals("test", logInfo.getThreadName());
    }
}