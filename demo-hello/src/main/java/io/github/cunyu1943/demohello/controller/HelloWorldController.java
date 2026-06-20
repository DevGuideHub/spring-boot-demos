package io.github.cunyu1943.demohello.controller;

import io.github.cunyu1943.demohello.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: Hello World 控制器
 * @author: cunyu1943
 * @date: 2026-04-16
 * @fileName: HelloWorldController
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@RestController
public class HelloWorldController {

    /**
     * 返回欢迎消息
     *
     * @return 统一响应结果
     */
    @GetMapping("/")
    public Result<String> index() {
        return Result.success("Hello World！");
    }

}