package io.github.cunyu1943.demoslf4jlogback.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @description: 统一响应结果测试
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@DisplayName("Result 统一响应结果测试")
class ResultTest {

    @Test
    @DisplayName("success - 无参数")
    void testSuccessWithoutData() {
        Result<String> result = Result.success();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertNull(result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("success - 有参数")
    void testSuccessWithData() {
        Result<String> result = Result.success("Hello");

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertEquals("Hello", result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("success - 自定义消息和数据")
    void testSuccessWithMessageAndData() {
        Result<String> result = Result.success("自定义消息", "数据");

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("自定义消息", result.getRespMsg());
        assertEquals("数据", result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("fail - 默认失败")
    void testFail() {
        Result<String> result = Result.fail("操作失败");

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("操作失败", result.getRespMsg());
        assertNull(result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("fail - 自定义状态码")
    void testFailWithCustomCode() {
        Result<String> result = Result.fail(400, "参数错误");

        assertNotNull(result);
        assertEquals(400, result.getRespCode());
        assertEquals("参数错误", result.getRespMsg());
        assertNull(result.getData());
        assertNotNull(result.getTimestamp());
    }

    @Test
    @DisplayName("success - 验证泛型类型")
    void testSuccessGenericType() {
        Result<Integer> intResult = Result.success(123);
        assertEquals(Integer.class, intResult.getData().getClass());

        Result<String> strResult = Result.success("test");
        assertEquals(String.class, strResult.getData().getClass());

        Result<Object> objResult = Result.success(new Object());
        assertEquals(Object.class, objResult.getData().getClass());
    }

    @Test
    @DisplayName("success - 验证时间戳是当前时间")
    void testSuccessTimestamp() {
        LocalDateTime before = LocalDateTime.now();
        Result<String> result = Result.success("test");
        LocalDateTime after = LocalDateTime.now();

        assertNotNull(result.getTimestamp());
        assertTrue(result.getTimestamp().isAfter(before.minusSeconds(1)));
        assertTrue(result.getTimestamp().isBefore(after.plusSeconds(1)));
    }

}