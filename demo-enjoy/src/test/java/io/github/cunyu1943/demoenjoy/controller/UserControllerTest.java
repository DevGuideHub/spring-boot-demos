package io.github.cunyu1943.demoenjoy.controller;

import io.github.cunyu1943.demoenjoy.dto.CreateUserRequest;
import io.github.cunyu1943.demoenjoy.dto.Result;
import io.github.cunyu1943.demoenjoy.dto.UserDTO;
import io.github.cunyu1943.demoenjoy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @description: 用户控制器测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserController 用户控制器测试")
class UserControllerTest {

    /**
     * 用户控制器
     */
    private UserController userController;

    /**
     * 用户服务 Mock
     */
    @Mock
    private UserService userService;

    /**
     * 模型 Mock
     */
    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
    }

    @Test
    @DisplayName("listUsers - 返回用户列表")
    void testListUsers() {
        UserDTO user1 = UserDTO.builder()
                .id(1L)
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        UserDTO user2 = UserDTO.builder()
                .id(2L)
                .username("lisi")
                .email("lisi@example.com")
                .build();
        List<UserDTO> users = Arrays.asList(user1, user2);
        Result<List<UserDTO>> expectedResult = Result.success(users);
        when(userService.listUsers()).thenReturn(expectedResult);

        Result<List<UserDTO>> result = userController.listUsers();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertNotNull(result.getData());
        assertEquals(2, result.getData().size());
        verify(userService, times(1)).listUsers();
    }

    @Test
    @DisplayName("getUserById - 成功获取用户")
    void testGetUserById() {
        UserDTO user = UserDTO.builder()
                .id(1L)
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        Result<UserDTO> expectedResult = Result.success(user);
        when(userService.getUserById(1L)).thenReturn(expectedResult);

        Result<UserDTO> result = userController.getUserById(1L);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("zhangsan", result.getData().getUsername());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    @DisplayName("getUserById - 用户不存在")
    void testGetUserByIdNotFound() {
        Result<UserDTO> expectedResult = Result.fail("用户不存在");
        when(userService.getUserById(999L)).thenReturn(expectedResult);

        Result<UserDTO> result = userController.getUserById(999L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
        verify(userService, times(1)).getUserById(999L);
    }

    @Test
    @DisplayName("createUser - 创建用户成功")
    void testCreateUser() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .build();
        UserDTO user = UserDTO.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();
        Result<UserDTO> expectedResult = Result.success(user);
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(expectedResult);

        Result<UserDTO> result = userController.createUser(request);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("testuser", result.getData().getUsername());
        verify(userService, times(1)).createUser(any(CreateUserRequest.class));
    }

    @Test
    @DisplayName("deleteUser - 删除用户成功")
    void testDeleteUser() {
        Result<Void> expectedResult = Result.success();
        when(userService.deleteUserById(1L)).thenReturn(expectedResult);

        Result<Void> result = userController.deleteUser(1L);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        verify(userService, times(1)).deleteUserById(1L);
    }

    @Test
    @DisplayName("deleteUser - 用户不存在")
    void testDeleteUserNotFound() {
        Result<Void> expectedResult = Result.fail("用户不存在");
        when(userService.deleteUserById(999L)).thenReturn(expectedResult);

        Result<Void> result = userController.deleteUser(999L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
        verify(userService, times(1)).deleteUserById(999L);
    }

    @Test
    @DisplayName("userPage - 渲染用户列表页面")
    void testUserPage() {
        List<UserDTO> users = Arrays.asList();
        Result<List<UserDTO>> expectedResult = Result.success(users);
        when(userService.listUsers()).thenReturn(expectedResult);

        String viewName = userController.userPage(model);

        assertEquals("user/list", viewName);
        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(model, times(1)).addAttribute("title", "用户列表");
        verify(userService, times(1)).listUsers();
    }

    @Test
    @DisplayName("welcome - 渲染欢迎页面")
    void testWelcome() {
        String viewName = userController.welcome("testuser", model);

        assertEquals("welcome", viewName);
        verify(model, times(1)).addAttribute("name", "testuser");
        verify(model, times(1)).addAttribute("message", "欢迎使用 Enjoy 模板引擎！");
    }

    @Test
    @DisplayName("controllerInstantiation - 控制器实例化")
    void testControllerInstantiation() {
        assertNotNull(userController);
        assertNotNull(userService);
    }

}
