package io.github.cunyu1943.demosbeetl.controller;

import io.github.cunyu1943.demosbeetl.dto.Result;
import io.github.cunyu1943.demosbeetl.dto.UserDTO;
import io.github.cunyu1943.demosbeetl.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    void setUp() {
        userController = new UserController(new UserServiceImpl());
    }

    @Test
    @DisplayName("listUsers - 返回用户列表")
    void testListUsers() {
        Result<List<UserDTO>> result = userController.listUsers();

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertFalse(result.getData().isEmpty());
    }

    @Test
    @DisplayName("getUser - 成功获取用户")
    void testGetUser() {
        Result<UserDTO> result = userController.getUser(1L);

        assertNotNull(result);
        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("zhangsan", result.getData().getUsername());
    }

    @Test
    @DisplayName("getUser - 用户不存在")
    void testGetUserNotFound() {
        Result<UserDTO> result = userController.getUser(999L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
    }

    @Test
    @DisplayName("getUser - 无效ID")
    void testGetUserInvalidId() {
        Result<UserDTO> result = userController.getUser(-1L);

        assertNotNull(result);
        assertEquals(500, result.getRespCode());
        assertEquals("用户ID无效", result.getRespMsg());
    }

    @Test
    @DisplayName("welcome - 返回欢迎页面")
    void testWelcome() {
        ModelAndView mv = userController.welcome("zhangsan");

        assertNotNull(mv);
        assertEquals("welcome", mv.getViewName());
        assertNotNull(mv.getModel().get("message"));
        assertEquals("zhangsan", mv.getModel().get("name"));
    }

    @Test
    @DisplayName("userPage - 返回用户列表页面")
    void testUserPage() {
        ModelAndView mv = userController.userPage();

        assertNotNull(mv);
        assertEquals("user-list", mv.getViewName());
        assertNotNull(mv.getModel().get("users"));
    }

}