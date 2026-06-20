package io.github.cunyu1943.demoswagger.controller;

import io.github.cunyu1943.demoswagger.dto.CreateUserRequest;
import io.github.cunyu1943.demoswagger.dto.Result;
import io.github.cunyu1943.demoswagger.dto.UserDTO;
import io.github.cunyu1943.demoswagger.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    /**
     * 用户服务
     */
    private final UserService userService;

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    @Operation(summary = "获取所有用户", description = "查询系统中所有用户信息")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Result<List<UserDTO>> listUsers() {
        log.info("请求 /api/users 接口");
        return userService.listUsers();
    }

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Operation(summary = "获取用户", description = "根据ID查询用户信息")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result<UserDTO> getUserById(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("请求 /api/users/{} 接口", id);
        return userService.getUserById(id);
    }

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 创建的用户信息
     */
    @Operation(summary = "创建用户", description = "创建一个新用户")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<UserDTO> createUser(
            @Parameter(description = "创建用户请求", required = true)
            @Valid @RequestBody CreateUserRequest request) {
        log.info("请求 POST /api/users 接口，请求: {}", request);
        return userService.createUser(request);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> deleteUser(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("请求 DELETE /api/users/{} 接口", id);
        return userService.deleteUserById(id);
    }

}