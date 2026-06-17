package io.github.cunyu1943.demothymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

/**
 * @description: Thymeleaf 控制器
 * @author: cunyu1943
 * @date: 2026-06-17
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@Controller
public class ThymeleafController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello Thymeleaf！");
        return "index";
    }

    @GetMapping("/user/{name}")
    public String user(Model model, @PathVariable String name) {
        model.addAttribute("name", name);
        return "user";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<String> items = Arrays.asList("Spring Boot", "Thymeleaf", "Mockito", "JUnit 5");
        model.addAttribute("items", items);
        return "list";
    }

    @GetMapping("/conditional/{flag}")
    public String conditional(Model model, @PathVariable boolean flag) {
        model.addAttribute("flag", flag);
        return "conditional";
    }
}
