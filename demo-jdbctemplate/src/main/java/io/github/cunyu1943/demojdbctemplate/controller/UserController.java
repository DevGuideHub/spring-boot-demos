package io.github.cunyu1943.demojdbctemplate.controller;

import io.github.cunyu1943.demojdbctemplate.entity.User;
import io.github.cunyu1943.demojdbctemplate.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 用户管理控制器（RESTful API）
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询所有用户
     */
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    /**
     * 新增用户
     *
     * @param user 用户信息
     */
    @PostMapping
    public String insert(@RequestBody User user) {
        return userService.insert(user) > 0 ? "添加成功" : "添加失败";
    }

    /**
     * 更新用户
     *
     * @param user 用户信息
     */
    @PutMapping
    public String update(@RequestBody User user) {
        return userService.update(user) > 0 ? "更新成功" : "更新失败";
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return userService.deleteById(id) > 0 ? "删除成功" : "删除失败";
    }

    /**
     * 统计用户总数
     */
    @GetMapping("/count")
    public String count() {
        return "用户总数: " + userService.count();
    }
}