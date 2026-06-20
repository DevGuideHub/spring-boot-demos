package io.github.cunyu1943.demolog4j2.controller;

import io.github.cunyu1943.demolog4j2.entity.LogInfo;
import io.github.cunyu1943.demolog4j2.service.Log4j2Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: Log4j2 测试控制器
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@RestController
@RequestMapping("/log4j2")
public class Log4j2Controller {

    private static final Logger logger = LogManager.getLogger(Log4j2Controller.class);

    private final Log4j2Service log4j2Service;

    public Log4j2Controller(Log4j2Service log4j2Service) {
        this.log4j2Service = log4j2Service;
    }

    /**
     * 首页
     */
    @GetMapping("/")
    public String index() {
        logger.info("访问 Log4j2 首页");
        return "Hello Log4j2！";
    }

    /**
     * 测试所有日志级别
     */
    @GetMapping("/test")
    public String testAllLevels() {
        log4j2Service.testAllLogLevels();
        return "Log4j2 日志测试完成！";
    }

    /**
     * 测试带参数的日志
     *
     * @param name 姓名
     * @param age  年龄
     */
    @GetMapping("/params")
    public String testParams(@RequestParam(defaultValue = "张三") String name,
                             @RequestParam(defaultValue = "25") int age) {
        log4j2Service.testLogWithParams(name, age);
        return "参数日志测试完成！";
    }

    /**
     * 测试异常日志
     */
    @GetMapping("/exception")
    public String testException() {
        log4j2Service.testExceptionLog();
        return "异常日志测试完成！";
    }

    /**
     * 获取日志信息对象
     *
     * @param level   日志级别
     * @param message 日志消息
     * @return 日志信息
     */
    @GetMapping("/info")
    public LogInfo getLogInfo(@RequestParam(defaultValue = "INFO") String level,
                              @RequestParam(defaultValue = "测试消息") String message) {
        return log4j2Service.getLogInfo(level, message);
    }

    /**
     * 测试嵌套日志
     */
    @GetMapping("/nested")
    public String testNested() {
        log4j2Service.testNestedLog();
        return "嵌套日志测试完成！";
    }
}