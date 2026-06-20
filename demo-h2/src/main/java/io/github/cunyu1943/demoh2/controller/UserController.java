
package io.github.cunyu1943.demoh2.controller;

import io.github.cunyu1943.demoh2.dto.CreateUserRequest;
import io.github.cunyu1943.demoh2.dto.Result;
import io.github.cunyu1943.demoh2.dto.UpdateUserRequest;
import io.github.cunyu1943.demoh2.dto.UserDTO;
import io.github.cunyu1943.demoh2.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @GetMapping
    public ResponseEntity<Result<List<UserDTO>>> listUsers() {
        log.info("请求 GET /api/users 接口");
        Result<List<UserDTO>> result = userService.listUsers();
        return ResponseEntity.ok(result);
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<UserDTO>> getUserById( @PathVariable Long id) {
        log.info("请求 GET /api/users/{} 接口", id);
        Result<UserDTO> result = userService.getUserById(id);
        if (result.getRespCode() == 404) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 创建的用户信息
     */
    @PostMapping
    public ResponseEntity<Result<UserDTO>> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("请求 POST /api/users 接口, 请求: {}", request);
        Result<UserDTO> result = userService.createUser(request);
        if (result.getRespCode() == 400) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.status(201).body(result);
    }

    /**
     * 更新用户
     *
     * @param id      用户ID
     * @param request 更新用户请求
     * @return 更新后的用户信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result<UserDTO>> updateUser(@PathVariable Long id,
                                                      @Valid @RequestBody UpdateUserRequest request) {
        log.info("请求 PUT /api/users/{} 接口, 请求: {}", id, request);
        Result<UserDTO> result = userService.updateUser(id, request);
        if (result.getRespCode() == 400) {
            return ResponseEntity.badRequest().body(result);
        }
        if (result.getRespCode() == 404) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteUser(@PathVariable Long id) {
        log.info("请求 DELETE /api/users/{} 接口", id);
        Result<Void> result = userService.deleteUser(id);
        if (result.getRespCode() == 404) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
