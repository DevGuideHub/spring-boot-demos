package io.github.cunyu1943.demojpa.controller;

import io.github.cunyu1943.demojpa.entity.User;
import io.github.cunyu1943.demojpa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 用户控制器
 * @author: cunyu1943
 * @date: 2026-06-18
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

    /**
     * 查询所有用户
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("查询所有用户");
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("查询用户：id={}", id);
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 创建用户
     *
     * @param user 用户对象
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            logger.info("创建用户：name={}", user.getName());
            User savedUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (IllegalArgumentException e) {
            logger.warn("创建用户失败：{}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 更新用户
     *
     * @param id   用户ID
     * @param user 更新内容
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            logger.info("更新用户：id={}", id);
            User updatedUser = userService.update(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            logger.warn("更新用户失败：{}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            logger.warn("更新用户失败：{}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("删除用户：id={}", id);
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 根据姓名查询用户
     *
     * @param name 姓名
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<User>> searchByName(@RequestParam String name) {
        logger.info("根据姓名查询用户：name={}", name);
        return ResponseEntity.ok(userService.findByName(name));
    }

    /**
     * 根据姓名模糊查询用户
     *
     * @param name 姓名关键词
     */
    @GetMapping("/search/name-contain")
    public ResponseEntity<List<User>> searchByNameContaining(@RequestParam String name) {
        logger.info("模糊查询用户：name={}", name);
        return ResponseEntity.ok(userService.findByNameContaining(name));
    }

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     */
    @GetMapping("/search/email")
    public ResponseEntity<User> searchByEmail(@RequestParam String email) {
        logger.info("根据邮箱查询用户：email={}", email);
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 根据年龄范围查询用户
     *
     * @param minAge 最小年龄
     * @param maxAge 最大年龄
     */
    @GetMapping("/search/age-range")
    public ResponseEntity<List<User>> searchByAgeRange(@RequestParam Integer minAge,
                                                        @RequestParam Integer maxAge) {
        logger.info("根据年龄范围查询用户：minAge={}, maxAge={}", minAge, maxAge);
        return ResponseEntity.ok(userService.findByAgeBetween(minAge, maxAge));
    }

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     */
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        logger.info("检查邮箱是否存在：email={}", email);
        return ResponseEntity.ok(userService.existsByEmail(email));
    }
}