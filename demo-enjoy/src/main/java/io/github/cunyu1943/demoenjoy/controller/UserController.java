package io.github.cunyu1943.demoenjoy.controller;

import io.github.cunyu1943.demoenjoy.dto.CreateUserRequest;
import io.github.cunyu1943.demoenjoy.dto.Result;
import io.github.cunyu1943.demoenjoy.dto.UserDTO;
import io.github.cunyu1943.demoenjoy.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/api/users")
public class UserController {

    /**
     * 用户服务
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询所有用户（API接口）
     *
     * @return 用户列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<List<UserDTO>> listUsers() {
        log.info("请求 GET /api/users/list 接口");
        return userService.listUsers();
    }

    /**
     * 根据ID查询用户（API接口）
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Result<UserDTO> getUserById(@PathVariable Long id) {
        log.info("请求 GET /api/users/{} 接口", id);
        return userService.getUserById(id);
    }

    /**
     * 创建用户（API接口）
     *
     * @param request 创建用户请求
     * @return 创建的用户信息
     */
    @PostMapping
    @ResponseBody
    public Result<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("请求 POST /api/users 接口，请求: {}", request);
        return userService.createUser(request);
    }

    /**
     * 删除用户（API接口）
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Result<Void> deleteUser(@PathVariable Long id) {
        log.info("请求 DELETE /api/users/{} 接口", id);
        return userService.deleteUserById(id);
    }

    /**
     * 渲染用户列表页面（Enjoy模板）
     *
     * @param model 模型数据
     * @return 模板名称
     */
    @GetMapping("/page")
    public String userPage(Model model) {
        log.info("请求 GET /api/users/page 页面");
        Result<List<UserDTO>> result = userService.listUsers();
        model.addAttribute("users", result.getData());
        model.addAttribute("title", "用户列表");
        return "user/list";
    }

    /**
     * 渲染欢迎页面（Enjoy模板）
     *
     * @param name 用户名
     * @param model 模型数据
     * @return 模板名称
     */
    @GetMapping("/welcome/{name}")
    public String welcome(@PathVariable String name, Model model) {
        log.info("请求 GET /api/users/welcome/{} 页面", name);
        model.addAttribute("name", name);
        model.addAttribute("message", "欢迎使用 Enjoy 模板引擎！");
        log.info("模型数据: name={}, message={}", name, "欢迎使用 Enjoy 模板引擎！");
        return "welcome";
    }

}
