package io.github.cunyu1943.demolog4j2.service.impl;

import io.github.cunyu1943.demolog4j2.entity.LogInfo;
import io.github.cunyu1943.demolog4j2.service.Log4j2Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description: Log4j2 服务实现类
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@Service
public class Log4j2ServiceImpl implements Log4j2Service {

    private static final Logger logger = LogManager.getLogger(Log4j2ServiceImpl.class);

    @Override
    public void testAllLogLevels() {
        logger.trace("Trace 日志 - 跟踪信息");
        logger.debug("Debug 日志 - 调试信息");
        logger.info("Info 日志 - 一般信息");
        logger.warn("Warn 日志 - 警告信息");
        logger.error("Error 日志 - 错误信息");
        logger.fatal("Fatal 日志 - 严重错误");
    }

    @Override
    public void testLogWithParams(String name, int age) {
        if (name == null || name.isEmpty()) {
            logger.warn("用户名为空");
            return;
        }
        if (age < 0 || age > 150) {
            logger.warn("年龄 {} 超出合理范围", age);
            return;
        }
        logger.info("用户信息：name = {}, age = {}", name, age);
    }

    @Override
    public void testExceptionLog() {
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            logger.error("发生算术异常", e);
        }
    }

    @Override
    public LogInfo getLogInfo(String level, String message) {
        String normalizedLevel = (level == null || level.isEmpty()) ? "UNKNOWN" : level.toUpperCase();
        return LogInfo.builder()
                .level(normalizedLevel)
                .message(message)
                .timestamp(LocalDateTime.now())
                .threadName(Thread.currentThread().getName())
                .build();
    }

    @Override
    public void testNestedLog() {
        logger.info("开始执行嵌套日志测试");
        innerMethod();
        logger.info("嵌套日志测试完成");
    }

    /**
     * 内部方法，测试嵌套调用
     */
    private void innerMethod() {
        logger.debug("执行内部方法");
        innerMethod2();
        logger.debug("内部方法执行完成");
    }

    /**
     * 第二层内部方法
     */
    private void innerMethod2() {
        logger.trace("执行第二层内部方法");
    }
}