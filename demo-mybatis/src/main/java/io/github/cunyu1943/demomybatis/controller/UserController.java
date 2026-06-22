package io.github.cunyu1943.demomybatis.controller;

import io.github.cunyu1943.demomybatis.config.ResourceNotFoundException;
import io.github.cunyu1943.demomybatis.dto.Result;
import io.github.cunyu1943.demomybatis.entity.User;
import io.github.cunyu1943.demomybatis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 用户控制器
 * @author: cunyu1943
 * @date: 2026-06-22
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Result<List<User>>> getAllUsers() {
        logger.info("查询所有用户");
        return ResponseEntity.ok(Result.success(userService.findAll(), "查询成功"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<User>> getUserById(@PathVariable Long id) {
        logger.info("查询用户：id={}", id);
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(Result.success(user, "查询成功")))
                .orElseThrow(() -> new ResourceNotFoundException("用户", id));
    }

    @PostMapping
    public ResponseEntity<Result<User>> createUser(@RequestBody User user) {
        logger.info("创建用户：userName={}", user.getUserName());
        user.setId(null);
        User savedUser = userService.save(user);
        return ResponseEntity.ok(Result.created(savedUser, "创建成功"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("更新用户：id={}", id);
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok(Result.success(updatedUser, "更新成功"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteUser(@PathVariable Long id) {
        logger.info("删除用户：id={}", id);
        userService.deleteById(id);
        return ResponseEntity.ok(Result.success(null, "删除成功"));
    }

    @GetMapping("/search/name")
    public ResponseEntity<Result<List<User>>> searchByName(@RequestParam String name) {
        logger.info("根据姓名查询用户：name={}", name);
        return ResponseEntity.ok(Result.success(userService.findByName(name), "查询成功"));
    }

    @GetMapping("/search/name-contain")
    public ResponseEntity<Result<List<User>>> searchByNameContaining(@RequestParam String name) {
        logger.info("模糊查询用户：name={}", name);
        return ResponseEntity.ok(Result.success(userService.findByNameContaining(name), "查询成功"));
    }

    @GetMapping("/search/email")
    public ResponseEntity<Result<User>> searchByEmail(@RequestParam String email) {
        logger.info("根据邮箱查询用户：email={}", email);
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(Result.success(user, "查询成功")))
                .orElseThrow(() -> new ResourceNotFoundException("用户不存在：email=" + email));
    }

    @GetMapping("/search/age-range")
    public ResponseEntity<Result<List<User>>> searchByAgeRange(@RequestParam Integer minAge,
                                                              @RequestParam Integer maxAge) {
        logger.info("根据年龄范围查询用户：minAge={}, maxAge={}", minAge, maxAge);
        return ResponseEntity.ok(Result.success(userService.findByAgeBetween(minAge, maxAge), "查询成功"));
    }

    @GetMapping("/check-email")
    public ResponseEntity<Result<Boolean>> checkEmailExists(@RequestParam String email) {
        logger.info("检查邮箱是否存在：email={}", email);
        return ResponseEntity.ok(Result.success(userService.existsByEmail(email), "查询成功"));
    }
}