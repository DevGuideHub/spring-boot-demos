package io.github.cunyu1943.demologback.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description: Logback 服务类，用于测试日志服务
 * @author: cunyu1943
 * @date: 2026-06-17
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Service
public class LogbackService {

    private static final Logger logger = LoggerFactory.getLogger(LogbackService.class);

    /**
     * 测试日志输出
     */
    public void testLog() {
        logger.info("LogbackService 初始化成功");
        logger.debug("调试信息：开始执行业务逻辑");
        logger.warn("警告信息：检测到非最优解");
        logger.error("错误信息：发生异常");
    }

    /**
     * 带参数的日志输出
     */
    public void testLogWithParams(String name, int age) {
        logger.info("用户信息：name = {}, age = {}", name, age);
    }

    /**
     * 测试异常日志
     */
    public void testExceptionLog() {
        try {
            int result = 10 / 0;
        } catch (Exception e) {
            logger.error("发生算术异常", e);
        }
    }
}
