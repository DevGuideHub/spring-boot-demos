package io.github.cunyu1943.demosbeetl.service.impl;

import io.github.cunyu1943.demosbeetl.dto.Result;
import io.github.cunyu1943.demosbeetl.dto.UserDTO;
import io.github.cunyu1943.demosbeetl.service.UserService;
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
    @DisplayName("listUsers - 返回用户列表")
    void testListUsers() {
        Result<List<UserDTO>> result = userService.listUsers();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertNotNull(result.getData());
        assertFalse(result.getData().isEmpty());
        assertEquals(3, result.getData().size());
    }

    @Test
    @DisplayName("getUserById - 成功获取用户")
    void testGetUserByIdSuccess() {
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
        assertNull(result.getData());
    }

    @Test
    @DisplayName("getUserById - ID为null")
    void testGetUserByIdNull() {
        Result<UserDTO> result = userService.getUserById(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("getUserById - ID小于等于0")
    void testGetUserByIdInvalid() {
        Result<UserDTO> result = userService.getUserById(0L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("getWelcomeMessage - 成功获取欢迎消息")
    void testGetWelcomeMessageSuccess() {
        Result<String> result = userService.getWelcomeMessage("zhangsan");

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("欢迎 zhangsan 来到 Spring Boot 世界！", result.getData());
    }

    @Test
    @DisplayName("getWelcomeMessage - 名称为null")
    void testGetWelcomeMessageNullName() {
        Result<String> result = userService.getWelcomeMessage(null);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("名称不能为空", result.getRespMsg());
    }

    @Test
    @DisplayName("getWelcomeMessage - 名称为空")
    void testGetWelcomeMessageEmptyName() {
        Result<String> result = userService.getWelcomeMessage("");

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("名称不能为空", result.getRespMsg());
    }

    @Test
    @DisplayName("listUsers - 验证用户数据完整性")
    void testListUsersDataIntegrity() {
        Result<List<UserDTO>> result = userService.listUsers();
        List<UserDTO> users = result.getData();

        for (UserDTO user : users) {
            assertNotNull(user.getId());
            assertNotNull(user.getUsername());
            assertNotNull(user.getEmail());
            assertNotNull(user.getCreateTime());
        }
    }

}