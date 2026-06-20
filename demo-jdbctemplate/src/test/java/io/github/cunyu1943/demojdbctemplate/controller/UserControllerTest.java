package io.github.cunyu1943.demojdbctemplate.controller;

import io.github.cunyu1943.demojdbctemplate.entity.User;
import io.github.cunyu1943.demojdbctemplate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @description: UserController 单元测试
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "张三", 25, "zhangsan@example.com");
    }

    @Test
    void testFindAll_ShouldReturnUserList() {
        List<User> users = Arrays.asList(testUser, new User(2L, "李四", 30, "lisi@example.com"));
        when(userService.findAll()).thenReturn(users);

        List<User> result = userController.findAll();

        assertEquals(2, result.size());
        verify(userService, times(1)).findAll();
    }

    @Test
    void testFindAll_WhenEmpty_ShouldReturnEmptyList() {
        when(userService.findAll()).thenReturn(Arrays.asList());

        List<User> result = userController.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById_ShouldReturnUser() {
        when(userService.findById(1L)).thenReturn(testUser);

        User result = userController.findById(1L);

        assertNotNull(result);
        assertEquals("张三", result.getName());
    }

    @Test
    void testInsert_ShouldReturnSuccessMessage() {
        when(userService.insert(any(User.class))).thenReturn(1);

        String result = userController.insert(testUser);

        assertEquals("添加成功", result);
    }

    @Test
    void testInsert_WhenFail_ShouldReturnFailMessage() {
        when(userService.insert(any(User.class))).thenReturn(0);

        String result = userController.insert(testUser);

        assertEquals("添加失败", result);
    }

    @Test
    void testUpdate_ShouldReturnSuccessMessage() {
        when(userService.update(any(User.class))).thenReturn(1);

        String result = userController.update(testUser);

        assertEquals("更新成功", result);
    }

    @Test
    void testUpdate_WhenFail_ShouldReturnFailMessage() {
        when(userService.update(any(User.class))).thenReturn(0);

        String result = userController.update(testUser);

        assertEquals("更新失败", result);
    }

    @Test
    void testDelete_ShouldReturnSuccessMessage() {
        when(userService.deleteById(1L)).thenReturn(1);

        String result = userController.delete(1L);

        assertEquals("删除成功", result);
    }

    @Test
    void testDelete_WhenFail_ShouldReturnFailMessage() {
        when(userService.deleteById(1L)).thenReturn(0);

        String result = userController.delete(1L);

        assertEquals("删除失败", result);
    }

    @Test
    void testCount_ShouldReturnCountMessage() {
        when(userService.count()).thenReturn(10);

        String result = userController.count();

        assertEquals("用户总数: 10", result);
    }

    @Test
    void testControllerInstantiation() {
        assertNotNull(userController);
    }
}