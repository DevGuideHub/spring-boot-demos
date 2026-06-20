package io.github.cunyu1943.demoswagger.controller;

import io.github.cunyu1943.demoswagger.dto.CreateUserRequest;
import io.github.cunyu1943.demoswagger.dto.Result;
import io.github.cunyu1943.demoswagger.dto.UserDTO;
import io.github.cunyu1943.demoswagger.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @description: 用户控制器测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserController 用户控制器测试")
class UserControllerTest {

    /**
     * 用户控制器
     */
    private UserController userController;

    /**
     * 用户服务
     */
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        userController = new UserController(userService);
    }

    @Test
    @DisplayName("listUsers - 返回用户列表")
    void testListUsers() {
        // 先创建一个用户
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        userService.createUser(request);

        Result<List<UserDTO>> result = userController.listUsers();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertNotNull(result.getData());
        assertFalse(result.getData().isEmpty());
    }

    @Test
    @DisplayName("getUserById - 成功获取用户")
    void testGetUserById() {
        // 先创建一个用户
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        userService.createUser(request);

        Result<UserDTO> result = userController.getUserById(1L);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("zhangsan", result.getData().getUsername());
    }

    @Test
    @DisplayName("getUserById - 用户不存在")
    void testGetUserByIdNotFound() {
        Result<UserDTO> result = userController.getUserById(999L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
    }

    @Test
    @DisplayName("createUser - 创建用户成功")
    void testCreateUser() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .build();

        Result<UserDTO> result = userController.createUser(request);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("testuser", result.getData().getUsername());
    }

    @Test
    @DisplayName("deleteUser - 删除用户成功")
    void testDeleteUser() {
        // 先创建一个用户
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        userService.createUser(request);

        Result<Void> result = userController.deleteUser(1L);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
    }

    @Test
    @DisplayName("deleteUser - 用户不存在")
    void testDeleteUserNotFound() {
        Result<Void> result = userController.deleteUser(999L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
    }

}