package io.github.cunyu1943.demomybatisflex.controller;

import io.github.cunyu1943.demomybatisflex.dto.Result;
import io.github.cunyu1943.demomybatisflex.entity.User;
import io.github.cunyu1943.demomybatisflex.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @description: 用户控制器单元测试
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: UserControllerTest
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

        @Mock
        private UserService userService;

        private UserController userController;

        private User user;

        @BeforeEach
        void setUp() {
                userController = new UserController(userService);
                user = User.builder()
                                .id(1L)
                                .name("张三")
                                .age(25)
                                .email("zhangsan@example.com")
                                .build();
        }

        @Test
        @DisplayName("查询所有用户 - 成功")
        void getAllUsers_Success() {
                List<User> users = Arrays.asList(user);
                when(userService.findAll()).thenReturn(users);

                ResponseEntity<Result<List<User>>> response = userController.getAllUsers();

                assertNotNull(response);
                Result<List<User>> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertEquals("操作成功", result.getRespMsg());
                assertNotNull(result.getData());
                assertEquals(1, result.getData().size());
                assertEquals("张三", result.getData().get(0).getName());
        }

        @Test
        @DisplayName("查询所有用户 - 空列表")
        void getAllUsers_EmptyList() {
                when(userService.findAll()).thenReturn(List.of());

                ResponseEntity<Result<List<User>>> response = userController.getAllUsers();

                assertNotNull(response);
                Result<List<User>> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertTrue(result.getData().isEmpty());
        }

        @Test
        @DisplayName("根据ID查询用户 - 成功")
        void getUserById_Success() {
                when(userService.findById(1L)).thenReturn(Optional.of(user));

                ResponseEntity<Result<User>> response = userController.getUserById(1L);

                assertNotNull(response);
                Result<User> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertEquals(1L, result.getData().getId());
                assertEquals("张三", result.getData().getName());
        }

        @Test
        @DisplayName("根据ID查询用户 - 不存在")
        void getUserById_NotFound() {
                when(userService.findById(99L)).thenReturn(Optional.empty());

                ResponseEntity<Result<User>> response = userController.getUserById(99L);

                assertNotNull(response);
                Result<User> result = response.getBody();
                assertNotNull(result);
                assertEquals(404, result.getRespCode());
                assertEquals("用户不存在", result.getRespMsg());
        }

        @Test
        @DisplayName("创建用户 - 成功")
        void createUser_Success() {
                User newUser = User.builder()
                                .name("李四")
                                .age(30)
                                .email("lisi@example.com")
                                .build();
                User savedUser = User.builder()
                                .id(2L)
                                .name("李四")
                                .age(30)
                                .email("lisi@example.com")
                                .build();

                when(userService.save(any(User.class))).thenReturn(savedUser);

                ResponseEntity<Result<User>> response = userController.createUser(newUser);

                assertNotNull(response);
                Result<User> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertEquals(2L, result.getData().getId());
                assertEquals("李四", result.getData().getName());
        }

        @Test
        @DisplayName("创建用户 - 参数异常")
        void createUser_IllegalArgumentException() {
                User newUser = User.builder()
                                .name("李四")
                                .age(30)
                                .email("zhangsan@example.com")
                                .build();

                when(userService.save(any(User.class)))
                                .thenThrow(new IllegalArgumentException("邮箱已存在"));

                IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
                                IllegalArgumentException.class,
                                () -> userController.createUser(newUser));
                assertEquals("邮箱已存在", exception.getMessage());
        }

        @Test
        @DisplayName("更新用户 - 成功")
        void updateUser_Success() {
                User updateUser = User.builder()
                                .name("张三-更新")
                                .age(26)
                                .email("zhangsan-update@example.com")
                                .build();
                User updatedUser = User.builder()
                                .id(1L)
                                .name("张三-更新")
                                .age(26)
                                .email("zhangsan-update@example.com")
                                .build();

                when(userService.findById(1L)).thenReturn(Optional.of(user));
                when(userService.save(any(User.class))).thenReturn(updatedUser);

                ResponseEntity<Result<User>> response = userController.updateUser(1L, updateUser);

                assertNotNull(response);
                Result<User> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertEquals("张三-更新", result.getData().getName());
        }

        @Test
    @DisplayName("更新用户 - 不存在")
    void updateUser_NotFound() {
        User updateUser = User.builder()
                .name("张三-更新")
                .build();

        when(userService.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> userController.updateUser(99L, updateUser)
        );
        assertEquals("用户不存在", exception.getMessage());
    }

        @Test
        @DisplayName("删除用户 - 成功")
        void deleteUser_Success() {
                when(userService.deleteById(1L)).thenReturn(true);

                ResponseEntity<Result<Void>> response = userController.deleteUser(1L);

                assertNotNull(response);
                Result<Void> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertEquals("操作成功", result.getRespMsg());
        }

        @Test
        @DisplayName("删除用户 - 不存在")
        void deleteUser_NotFound() {
                when(userService.deleteById(99L)).thenReturn(false);

                ResponseEntity<Result<Void>> response = userController.deleteUser(99L);

                assertNotNull(response);
                Result<Void> result = response.getBody();
                assertNotNull(result);
                assertEquals(404, result.getRespCode());
                assertEquals("用户不存在", result.getRespMsg());
        }

        @Test
        @DisplayName("根据姓名查询用户 - 成功")
        void searchByName_Success() {
                List<User> users = Arrays.asList(user);
                when(userService.findByName(anyString())).thenReturn(users);

                ResponseEntity<Result<List<User>>> response = userController.searchByName("张三");

                assertNotNull(response);
                Result<List<User>> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertEquals(1, result.getData().size());
                assertEquals("张三", result.getData().get(0).getName());
        }

        @Test
        @DisplayName("根据姓名查询用户 - 空列表")
        void searchByName_EmptyList() {
                when(userService.findByName(anyString())).thenReturn(List.of());

                ResponseEntity<Result<List<User>>> response = userController.searchByName("不存在");

                assertNotNull(response);
                Result<List<User>> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertTrue(result.getData().isEmpty());
        }

        @Test
        @DisplayName("根据邮箱查询用户 - 成功")
        void searchByEmail_Success() {
                when(userService.findByEmail(anyString())).thenReturn(Optional.of(user));

                ResponseEntity<Result<User>> response = userController.searchByEmail("zhangsan@example.com");

                assertNotNull(response);
                Result<User> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertEquals("zhangsan@example.com", result.getData().getEmail());
        }

        @Test
        @DisplayName("根据邮箱查询用户 - 不存在")
        void searchByEmail_NotFound() {
                when(userService.findByEmail(anyString())).thenReturn(Optional.empty());

                ResponseEntity<Result<User>> response = userController.searchByEmail("notfound@example.com");

                assertNotNull(response);
                Result<User> result = response.getBody();
                assertNotNull(result);
                assertEquals(404, result.getRespCode());
                assertEquals("用户不存在", result.getRespMsg());
        }

        @Test
        @DisplayName("根据年龄范围查询用户 - 成功")
        void searchByAgeRange_Success() {
                List<User> users = Arrays.asList(user);
                when(userService.findByAgeBetween(anyInt(), anyInt())).thenReturn(users);

                ResponseEntity<Result<List<User>>> response = userController.searchByAgeRange(20, 30);

                assertNotNull(response);
                Result<List<User>> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertEquals(1, result.getData().size());
                assertEquals(25, result.getData().get(0).getAge());
        }

        @Test
        @DisplayName("根据年龄范围查询用户 - 空列表")
        void searchByAgeRange_EmptyList() {
                when(userService.findByAgeBetween(anyInt(), anyInt())).thenReturn(List.of());

                ResponseEntity<Result<List<User>>> response = userController.searchByAgeRange(100, 200);

                assertNotNull(response);
                Result<List<User>> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertTrue(result.getData().isEmpty());
        }

        @Test
        @DisplayName("检查邮箱是否存在 - 存在")
        void checkEmailExists_Exists() {
                when(userService.existsByEmail(anyString())).thenReturn(true);

                ResponseEntity<Result<Boolean>> response = userController.checkEmailExists("zhangsan@example.com");

                assertNotNull(response);
                Result<Boolean> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertTrue(result.getData());
        }

        @Test
        @DisplayName("检查邮箱是否存在 - 不存在")
        void checkEmailExists_NotExists() {
                when(userService.existsByEmail(anyString())).thenReturn(false);

                ResponseEntity<Result<Boolean>> response = userController.checkEmailExists("notfound@example.com");

                assertNotNull(response);
                Result<Boolean> result = response.getBody();
                assertNotNull(result);
                assertEquals(200, result.getRespCode());
                assertNotNull(result.getData());
                assertFalse(result.getData());
        }
}