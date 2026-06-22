package io.github.cunyu1943.demomybatisflex.controller;

import io.github.cunyu1943.demomybatisflex.dto.Result;
import io.github.cunyu1943.demomybatisflex.entity.User;
import io.github.cunyu1943.demomybatisflex.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 用户控制器
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: UserController
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询所有用户
     *
     * @return 统一响应结果
     */
    @GetMapping
    public ResponseEntity<Result<List<User>>> getAllUsers() {
        logger.info("查询所有用户");
        List<User> users = userService.findAll();
        return ResponseEntity.ok(Result.success(users));
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 统一响应结果
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<User>> getUserById(@PathVariable Long id) {
        logger.info("查询用户：id={}", id);
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(Result.success(user)))
                .orElse(ResponseEntity.ok(Result.fail(404, "用户不存在")));
    }

    /**
     * 创建用户
     *
     * @param user 用户对象
     * @return 统一响应结果
     */
    @PostMapping
    public ResponseEntity<Result<User>> createUser(@RequestBody User user) {
        logger.info("创建用户：name={}", user.getName());
        user.setId(null);
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success(savedUser));
    }

    /**
     * 更新用户
     *
     * @param id   用户ID
     * @param user 更新内容
     * @return 统一响应结果
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("更新用户：id={}", id);
        User existingUser = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setId(id);
        User updatedUser = userService.save(user);
        return ResponseEntity.ok(Result.success(updatedUser));
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 统一响应结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteUser(@PathVariable Long id) {
        logger.info("删除用户：id={}", id);
        boolean deleted = userService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok(Result.success());
        }
        return ResponseEntity.ok(Result.fail(404, "用户不存在"));
    }

    /**
     * 根据姓名查询用户
     *
     * @param name 姓名
     * @return 统一响应结果
     */
    @GetMapping("/search/name")
    public ResponseEntity<Result<List<User>>> searchByName(@RequestParam String name) {
        logger.info("根据姓名查询用户：name={}", name);
        List<User> users = userService.findByName(name);
        return ResponseEntity.ok(Result.success(users));
    }

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 统一响应结果
     */
    @GetMapping("/search/email")
    public ResponseEntity<Result<User>> searchByEmail(@RequestParam String email) {
        logger.info("根据邮箱查询用户：email={}", email);
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(Result.success(user)))
                .orElse(ResponseEntity.ok(Result.fail(404, "用户不存在")));
    }

    /**
     * 根据年龄范围查询用户
     *
     * @param minAge 最小年龄
     * @param maxAge 最大年龄
     * @return 统一响应结果
     */
    @GetMapping("/search/age-range")
    public ResponseEntity<Result<List<User>>> searchByAgeRange(@RequestParam Integer minAge,
            @RequestParam Integer maxAge) {
        logger.info("根据年龄范围查询用户：minAge={}, maxAge={}", minAge, maxAge);
        List<User> users = userService.findByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok(Result.success(users));
    }

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 统一响应结果
     */
    @GetMapping("/check-email")
    public ResponseEntity<Result<Boolean>> checkEmailExists(@RequestParam String email) {
        logger.info("检查邮箱是否存在：email={}", email);
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(Result.success(exists));
    }
}
