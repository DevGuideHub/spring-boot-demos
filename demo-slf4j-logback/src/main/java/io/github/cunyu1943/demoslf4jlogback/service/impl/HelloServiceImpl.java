package io.github.cunyu1943.demoslf4jlogback.service.impl;

import io.github.cunyu1943.demoslf4jlogback.dto.Result;
import io.github.cunyu1943.demoslf4jlogback.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @description: Hello服务实现类
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: HelloServiceImpl
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

    /**
     * 获取Hello消息
     *
     * @return Hello消息
     */
    @Override
    public Result<String> getHello() {
        log.info("调用 getHello 方法");
        String message = "Hello World！";
        log.debug("返回消息: {}", message);
        return Result.success(message);
    }

    /**
     * 获取自定义消息
     *
     * @param name 名称
     * @return 自定义消息
     */
    @Override
    public Result<String> getCustomMessage(String name) {
        log.info("调用 getCustomMessage 方法，参数: {}", name);
        if (Objects.isNull(name) || name.isEmpty()) {
            log.warn("参数 name 为空");
            return Result.fail("名称不能为空");
        }
        String message = "Hello, " + name + "！";
        log.debug("返回消息: {}", message);
        return Result.success(message);
    }

    /**
     * 测试错误日志
     *
     * @return 错误日志测试结果
     */
    @Override
    public Result<String> testErrorLog() {
        log.info("调用 testErrorLog 方法");
        try {
            // 模拟一个异常
            throw new RuntimeException("这是一个测试异常");
        } catch (RuntimeException e) {
            log.error("捕获到异常: {}", e.getMessage(), e);
        }
        return Result.success("错误日志测试完成");
    }

}