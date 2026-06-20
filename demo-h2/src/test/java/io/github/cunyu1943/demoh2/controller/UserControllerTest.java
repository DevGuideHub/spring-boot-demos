package io.github.cunyu1943.demoh2.controller;

import io.github.cunyu1943.demoh2.dto.CreateUserRequest;
import io.github.cunyu1943.demoh2.dto.Result;
import io.github.cunyu1943.demoh2.dto.UpdateUserRequest;
import io.github.cunyu1943.demoh2.dto.UserDTO;
import io.github.cunyu1943.demoh2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * @description: 用户控制器测试
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: UserControllerTest
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

    private UserDTO mockUserDTO;

    @BeforeEach
    void setUp() {
        mockUserDTO = new UserDTO();
        mockUserDTO.setId(1L);
        mockUserDTO.setUsername("testuser");
        mockUserDTO.setEmail("test@example.com");
        mockUserDTO.setCreatedAt(LocalDateTime.now());
        mockUserDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("查询所有用户 - 成功")
    void listUsers_Success() {
        List<UserDTO> userList = Arrays.asList(mockUserDTO);
        when(userService.listUsers()).thenReturn(Result.success(userList));

        ResponseEntity<Result<List<UserDTO>>> response = userController.listUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getRespCode());
        assertEquals("操作成功", response.getBody().getRespMsg());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    @DisplayName("根据ID查询用户 - 成功")
    void getUserById_Success() {
        when(userService.getUserById(1L)).thenReturn(Result.success(mockUserDTO));

        ResponseEntity<Result<UserDTO>> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getRespCode());
        assertEquals("testuser", response.getBody().getData().getUsername());
    }

    @Test
    @DisplayName("根据ID查询用户 - 用户不存在")
    void getUserById_NotFound() {
        when(userService.getUserById(99L)).thenReturn(Result.fail(404, "用户不存在"));

        ResponseEntity<Result<UserDTO>> response = userController.getUserById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getRespCode());
        assertEquals("用户不存在", response.getBody().getRespMsg());
    }

    @Test
    @DisplayName("创建用户 - 成功")
    void createUser_Success() {
        CreateUserRequest request = new CreateUserRequest("newuser", "new@example.com", "password123");
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(Result.success(mockUserDTO));

        ResponseEntity<Result<UserDTO>> response = userController.createUser(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("创建用户 - 失败")
    void createUser_Failure() {
        CreateUserRequest request = new CreateUserRequest("existing", "test@example.com", "password123");
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(Result.fail(400, "用户名已存在"));

        ResponseEntity<Result<UserDTO>> response = userController.createUser(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getRespCode());
        assertEquals("用户名已存在", response.getBody().getRespMsg());
    }

    @Test
    @DisplayName("更新用户 - 成功")
    void updateUser_Success() {
        UpdateUserRequest request = new UpdateUserRequest("updateduser", null, null);
        when(userService.updateUser(eq(1L), any(UpdateUserRequest.class))).thenReturn(Result.success(mockUserDTO));

        ResponseEntity<Result<UserDTO>> response = userController.updateUser(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("更新用户 - 用户不存在")
    void updateUser_NotFound() {
        UpdateUserRequest request = new UpdateUserRequest("updateduser", null, null);
        when(userService.updateUser(eq(99L), any(UpdateUserRequest.class))).thenReturn(Result.fail(404, "用户不存在"));

        ResponseEntity<Result<UserDTO>> response = userController.updateUser(99L, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("删除用户 - 成功")
    void deleteUser_Success() {
        when(userService.deleteUser(1L)).thenReturn(Result.success());

        ResponseEntity<Result<Void>> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("删除用户 - 用户不存在")
    void deleteUser_NotFound() {
        when(userService.deleteUser(99L)).thenReturn(Result.fail(404, "用户不存在"));

        ResponseEntity<Result<Void>> response = userController.deleteUser(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getRespCode());
    }
}