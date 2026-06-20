package io.github.cunyu1943.demoswagger.service.impl;

import io.github.cunyu1943.demoswagger.dto.CreateUserRequest;
import io.github.cunyu1943.demoswagger.dto.Result;
import io.github.cunyu1943.demoswagger.dto.UserDTO;
import io.github.cunyu1943.demoswagger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: 用户服务实现测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService 用户服务测试")
class UserServiceImplTest {

    /**
     * 用户服务实现
     */
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    @DisplayName("listUsers - 空列表")
    void testListUsersEmpty() {
        Result<List<UserDTO>> result = userService.listUsers();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertNotNull(result.getData());
        assertTrue(result.getData().isEmpty());
    }

    @Test
    @DisplayName("listUsers - 有数据")
    void testListUsersWithData() {
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
        assertEquals("zhangsan", result.getData().get(0).getUsername());
    }

    @Test
    @DisplayName("getUserById - 用户不存在")
    void testGetUserByIdNotFound() {
        Result<UserDTO> result = userService.getUserById(999L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("getUserById - ID无效（null）")
    void testGetUserByIdNullId() {
        Result<UserDTO> result = userService.getUserById(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("getUserById - ID无效（小于等于0）")
    void testGetUserByIdInvalidId() {
        Result<UserDTO> result = userService.getUserById(0L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("createUser - 创建用户成功")
    void testCreateUserSuccess() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();

        Result<UserDTO> result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertNotNull(result.getData());
        assertEquals("zhangsan", result.getData().getUsername());
        assertEquals("zhangsan@example.com", result.getData().getEmail());
        assertNotNull(result.getData().getId());
    }

    @Test
    @DisplayName("createUser - 用户名为空")
    void testCreateUserEmptyUsername() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("")
                .email("zhangsan@example.com")
                .build();

        Result<UserDTO> result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户名不能为空", result.getRespMsg());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("createUser - 用户名为null")
    void testCreateUserNullUsername() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username(null)
                .email("zhangsan@example.com")
                .build();

        Result<UserDTO> result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户名不能为空", result.getRespMsg());
    }

    @Test
    @DisplayName("createUser - request为null")
    void testCreateUserNullRequest() {
        Result<UserDTO> result = userService.createUser(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户名不能为空", result.getRespMsg());
    }

    @Test
    @DisplayName("deleteUserById - 删除成功")
    void testDeleteUserByIdSuccess() {
        // 先创建用户
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        Result<UserDTO> createResult = userService.createUser(request);
        Long userId = createResult.getData().getId();

        Result<Void> result = userService.deleteUserById(userId);

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
    @DisplayName("deleteUserById - ID无效（null）")
    void testDeleteUserByIdNullId() {
        Result<Void> result = userService.deleteUserById(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("deleteUserById - ID无效（小于等于0）")
    void testDeleteUserByIdInvalidId() {
        Result<Void> result = userService.deleteUserById(-1L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("getUserById - 创建后查询")
    void testGetUserByIdAfterCreate() {
        // 创建用户
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        Result<UserDTO> createResult = userService.createUser(request);
        Long userId = createResult.getData().getId();

        // 查询用户
        Result<UserDTO> getResult = userService.getUserById(userId);

        assertNotNull(getResult);
        assertEquals(200, getResult.getRespCode());
        assertNotNull(getResult.getData());
        assertEquals("zhangsan", getResult.getData().getUsername());
    }

}