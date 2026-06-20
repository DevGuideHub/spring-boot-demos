package io.github.cunyu1943.demoenjoy.service;

import io.github.cunyu1943.demoenjoy.dto.CreateUserRequest;
import io.github.cunyu1943.demoenjoy.dto.Result;
import io.github.cunyu1943.demoenjoy.dto.UserDTO;
import io.github.cunyu1943.demoenjoy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @description: 用户服务测试（使用 JUnit 5）
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 用户服务测试")
class UserServiceTest {

    /**
     * 用户服务
     */
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    @DisplayName("listUsers - 返回空列表")
    void testListUsersEmpty() {
        Result<List<UserDTO>> result = userService.listUsers();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertNotNull(result.getData());
        assertTrue(result.getData().isEmpty());
    }

    @Test
    @DisplayName("listUsers - 返回用户列表")
    void testListUsers() {
        // 先创建用户
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        userService.createUser(request);

        Result<List<UserDTO>> result = userService.listUsers();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals(1, result.getData().size());
    }

    @Test
    @DisplayName("getUserById - 成功获取用户")
    void testGetUserById() {
        // 先创建用户
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        userService.createUser(request);

        Result<UserDTO> result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("zhangsan", result.getData().getUsername());
    }

    @Test
    @DisplayName("getUserById - 用户不存在")
    void testGetUserByIdNotFound() {
        Result<UserDTO> result = userService.getUserById(999L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
    }

    @Test
    @DisplayName("getUserById - ID 无效（null）")
    void testGetUserByIdNull() {
        Result<UserDTO> result = userService.getUserById(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("getUserById - ID 无效（<= 0）")
    void testGetUserByIdInvalid() {
        Result<UserDTO> result = userService.getUserById(0L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("createUser - 创建用户成功")
    void testCreateUser() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .build();

        Result<UserDTO> result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("testuser", result.getData().getUsername());
        assertEquals("test@example.com", result.getData().getEmail());
    }

    @Test
    @DisplayName("createUser - 用户名为空")
    void testCreateUserEmptyUsername() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("")
                .email("test@example.com")
                .build();

        Result<UserDTO> result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户名不能为空", result.getRespMsg());
    }

    @Test
    @DisplayName("createUser - 用户名为 null")
    void testCreateUserNullUsername() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username(null)
                .email("test@example.com")
                .build();

        Result<UserDTO> result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户名不能为空", result.getRespMsg());
    }

    @Test
    @DisplayName("createUser - 请求为 null")
    void testCreateUserNullRequest() {
        Result<UserDTO> result = userService.createUser(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户名不能为空", result.getRespMsg());
    }

    @Test
    @DisplayName("deleteUserById - 删除用户成功")
    void testDeleteUserById() {
        // 先创建用户
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        userService.createUser(request);

        Result<Void> result = userService.deleteUserById(1L);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
    }

    @Test
    @DisplayName("deleteUserById - 用户不存在")
    void testDeleteUserByIdNotFound() {
        Result<Void> result = userService.deleteUserById(999L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
    }

    @Test
    @DisplayName("deleteUserById - ID 无效（null）")
    void testDeleteUserByIdNull() {
        Result<Void> result = userService.deleteUserById(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("deleteUserById - ID 无效（<= 0）")
    void testDeleteUserByIdInvalid() {
        Result<Void> result = userService.deleteUserById(-1L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("serviceInstantiation - 服务实例化")
    void testServiceInstantiation() {
        assertNotNull(userService);
    }

}
