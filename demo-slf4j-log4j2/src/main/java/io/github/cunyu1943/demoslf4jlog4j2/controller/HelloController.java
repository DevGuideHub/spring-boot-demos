package io.github.cunyu1943.demoslf4jlog4j2.controller;

import io.github.cunyu1943.demoslf4jlog4j2.dto.Result;
import io.github.cunyu1943.demoslf4jlog4j2.service.service.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: Hello控制器
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: HelloController
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HelloController {

    /**
     * Hello服务
     */
    private final HelloService helloService;

    /**
     * 获取Hello消息
     *
     * @return Hello消息
     */
    @GetMapping("/hello")
    public Result<String> hello() {
        log.info("请求 /api/hello 接口");
        return helloService.getHello();
    }

    /**
     * 获取自定义消息
     *
     * @param name 名称
     * @return 自定义消息
     */
    @GetMapping("/hello/{name}")
    public Result<String> customHello(@PathVariable String name) {
        log.info("请求 /api/hello/{} 接口", name);
        return helloService.getCustomMessage(name);
    }

    /**
     * 测试错误日志
     *
     * @return 错误日志测试结果
     */
    @GetMapping("/error")
    public Result<String> testError() {
        log.info("请求 /api/error 接口");
        return helloService.testErrorLog();
    }

}