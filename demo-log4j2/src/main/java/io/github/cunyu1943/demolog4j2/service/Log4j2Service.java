package io.github.cunyu1943.demolog4j2.service;

import io.github.cunyu1943.demolog4j2.entity.LogInfo;

/**
 * @description: Log4j2 服务接口
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

public interface Log4j2Service {

    /**
     * 测试所有日志级别输出
     */
    void testAllLogLevels();

    /**
     * 带参数的日志输出
     *
     * @param name 姓名
     * @param age  年龄
     */
    void testLogWithParams(String name, int age);

    /**
     * 测试异常日志
     */
    void testExceptionLog();

    /**
     * 获取日志信息对象
     *
     * @param level   日志级别
     * @param message 日志消息
     * @return 日志信息对象
     */
    LogInfo getLogInfo(String level, String message);

    /**
     * 测试嵌套日志
     */
    void testNestedLog();
}