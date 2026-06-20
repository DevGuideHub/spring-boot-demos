package io.github.cunyu1943.demosbeetl.controller;

import io.github.cunyu1943.demosbeetl.dto.Result;
import io.github.cunyu1943.demosbeetl.dto.UserDTO;
import io.github.cunyu1943.demosbeetl.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @description: 用户控制器
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: UserController
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    /**
     * 用户服务
     */
    private final UserService userService;

    /**
     * 欢迎页面
     *
     * @param name 名称
     * @return ModelAndView
     */
    @GetMapping("/welcome/{name}")
    public ModelAndView welcome(@PathVariable String name) {
        log.info("请求 /api/welcome/{} 接口", name);
        Result<String> result = userService.getWelcomeMessage(name);
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("welcome");
        mv.addObject("message", result.getData());
        mv.addObject("name", name);
        return mv;
    }

    /**
     * 获取所有用户（API）
     *
     * @return 用户列表
     */
    @GetMapping("/users")
    @ResponseBody
    public Result<List<UserDTO>> listUsers() {
        log.info("请求 /api/users 接口");
        return userService.listUsers();
    }

    /**
     * 获取用户（API）
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/users/{id}")
    @ResponseBody
    public Result<UserDTO> getUser(@PathVariable Long id) {
        log.info("请求 /api/users/{} 接口", id);
        return userService.getUserById(id);
    }

    /**
     * 用户列表页面
     *
     * @return ModelAndView
     */
    @GetMapping("/users/page")
    public ModelAndView userPage() {
        log.info("请求 /api/users/page 接口");
        Result<List<UserDTO>> result = userService.listUsers();
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user-list");
        mv.addObject("users", result.getData());
        return mv;
    }

}