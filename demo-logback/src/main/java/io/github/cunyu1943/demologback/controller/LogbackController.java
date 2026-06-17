package io.github.cunyu1943.demologback.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: Logback 测试控制器
 * @author: cunyu1943
 * @date: 2026-06-17
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@RestController
public class LogbackController {

    private static final Logger logger = LoggerFactory.getLogger(LogbackController.class);

    @GetMapping("/log")
    public String testLog() {
        // 测试不同级别的日志输出
        logger.trace("Trace 日志 - 跟踪信息");
        logger.debug("Debug 日志 - 调试信息");
        logger.info("Info 日志 - 一般信息");
        logger.warn("Warn 日志 - 警告信息");
        logger.error("Error 日志 - 错误信息");
        return "Logback 日志测试完成！";
    }

    @GetMapping("/")
    public String index() {
        logger.info("访问首页");
        return "Hello Logback！";
    }
}
