package io.github.cunyu1943.demojpa.controller;

import io.github.cunyu1943.demojpa.entity.User;
import io.github.cunyu1943.demojpa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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
        testUser = User.builder()
                .id(1L)
                .name("张三")
                .age(25)
                .email("zhangsan@example.com")
                .build();
    }

    @Test
    void testGetAllUsers_ShouldReturnUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userService.findAll()).thenReturn(users);

        ResponseEntity<List<User>> result = userController.getAllUsers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        verify(userService, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_EmptyList_ShouldReturnEmpty() {
        when(userService.findAll()).thenReturn(List.of());

        ResponseEntity<List<User>> result = userController.getAllUsers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        verify(userService, times(1)).findAll();
    }

    @Test
    void testGetUserById_NormalCase_ShouldReturnUser() {
        when(userService.findById(1L)).thenReturn(Optional.of(testUser));

        ResponseEntity<User> result = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("张三", result.getBody().getName());
        verify(userService, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UserNotFound_ShouldReturnNotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.getUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(userService, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NullId_ShouldReturnNotFound() {
        when(userService.findById(null)).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.getUserById(null);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(userService, times(1)).findById(null);
    }

    @Test
    void testCreateUser_NormalCase_ShouldReturnCreated() {
        when(userService.save(any(User.class))).thenReturn(testUser);

        ResponseEntity<?> result = userController.createUser(testUser);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_IllegalArgumentException_ShouldReturnBadRequest() {
        when(userService.save(any(User.class))).thenThrow(new IllegalArgumentException("邮箱已存在"));

        ResponseEntity<?> result = userController.createUser(testUser);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("邮箱已存在", result.getBody());
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_NormalCase_ShouldReturnUpdatedUser() {
        when(userService.update(eq(1L), any(User.class))).thenReturn(testUser);

        ResponseEntity<?> result = userController.updateUser(1L, testUser);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(userService, times(1)).update(eq(1L), any(User.class));
    }

    @Test
    void testUpdateUser_IllegalArgumentException_ShouldReturnBadRequest() {
        when(userService.update(eq(1L), any(User.class))).thenThrow(new IllegalArgumentException("用户ID无效"));

        ResponseEntity<?> result = userController.updateUser(1L, testUser);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("用户ID无效", result.getBody());
        verify(userService, times(1)).update(eq(1L), any(User.class));
    }

    @Test
    void testUpdateUser_RuntimeException_ShouldReturnNotFound() {
        when(userService.update(eq(1L), any(User.class))).thenThrow(new RuntimeException("用户不存在"));

        ResponseEntity<?> result = userController.updateUser(1L, testUser);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(userService, times(1)).update(eq(1L), any(User.class));
    }

    @Test
    void testDeleteUser_NormalCase_ShouldReturnNoContent() {
        doNothing().when(userService).deleteById(1L);

        ResponseEntity<Void> result = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NullId_ShouldReturnNoContent() {
        doNothing().when(userService).deleteById(null);

        ResponseEntity<Void> result = userController.deleteUser(null);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(userService, times(1)).deleteById(null);
    }

    @Test
    void testSearchByName_NormalCase_ShouldReturnUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userService.findByName("张三")).thenReturn(users);

        ResponseEntity<List<User>> result = userController.searchByName("张三");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        verify(userService, times(1)).findByName("张三");
    }

    @Test
    void testSearchByName_EmptyName_ShouldReturnEmpty() {
        when(userService.findByName("")).thenReturn(List.of());

        ResponseEntity<List<User>> result = userController.searchByName("");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        verify(userService, times(1)).findByName("");
    }

    @Test
    void testSearchByNameContaining_NormalCase_ShouldReturnUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userService.findByNameContaining("张")).thenReturn(users);

        ResponseEntity<List<User>> result = userController.searchByNameContaining("张");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        verify(userService, times(1)).findByNameContaining("张");
    }

    @Test
    void testSearchByEmail_NormalCase_ShouldReturnUser() {
        when(userService.findByEmail("zhangsan@example.com")).thenReturn(Optional.of(testUser));

        ResponseEntity<User> result = userController.searchByEmail("zhangsan@example.com");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("张三", result.getBody().getName());
        verify(userService, times(1)).findByEmail("zhangsan@example.com");
    }

    @Test
    void testSearchByEmail_UserNotFound_ShouldReturnNotFound() {
        when(userService.findByEmail("notexist@example.com")).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.searchByEmail("notexist@example.com");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(userService, times(1)).findByEmail("notexist@example.com");
    }

    @Test
    void testSearchByAgeRange_NormalCase_ShouldReturnUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userService.findByAgeBetween(20, 30)).thenReturn(users);

        ResponseEntity<List<User>> result = userController.searchByAgeRange(20, 30);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        verify(userService, times(1)).findByAgeBetween(20, 30);
    }

    @Test
    void testSearchByAgeRange_InvalidRange_ShouldReturnEmpty() {
        when(userService.findByAgeBetween(30, 20)).thenReturn(List.of());

        ResponseEntity<List<User>> result = userController.searchByAgeRange(30, 20);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        verify(userService, times(1)).findByAgeBetween(30, 20);
    }

    @Test
    void testCheckEmailExists_EmailExists_ShouldReturnTrue() {
        when(userService.existsByEmail("zhangsan@example.com")).thenReturn(true);

        ResponseEntity<Boolean> result = userController.checkEmailExists("zhangsan@example.com");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody());
        verify(userService, times(1)).existsByEmail("zhangsan@example.com");
    }

    @Test
    void testCheckEmailExists_EmailNotExists_ShouldReturnFalse() {
        when(userService.existsByEmail("notexist@example.com")).thenReturn(false);

        ResponseEntity<Boolean> result = userController.checkEmailExists("notexist@example.com");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertFalse(result.getBody());
        verify(userService, times(1)).existsByEmail("notexist@example.com");
    }

    @Test
    void testControllerInstantiation() {
        assertNotNull(userController);
    }
}