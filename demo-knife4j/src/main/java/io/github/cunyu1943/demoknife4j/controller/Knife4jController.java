package io.github.cunyu1943.demoknife4j.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: Hello World 控制器
 * @author: cunyu1943
 * @date: 2026-06-16
 * @fileName: Knife4jController
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@RestController
@RequestMapping("/api")
@Tag(name = "Hello World", description = "Hello World API")
public class Knife4jController {

    @GetMapping("/hello")
    @Operation(summary = "Hello World", description = "返回 Hello World 消息")
    public String hello() {
        return "Hello World！";
    }

    @GetMapping("/greeting")
    @Parameters({@Parameter(name = "name", description = "姓名", required = false)})
    @Operation(summary = "问候", description = "返回问候消息")
    public String greeting(String name) {
        return "Hello, " + (name != null ? name : "Guest") + "！";
    }
}
