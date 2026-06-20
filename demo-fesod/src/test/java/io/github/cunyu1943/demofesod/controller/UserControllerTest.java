package io.github.cunyu1943.demofesod.controller;

import io.github.cunyu1943.demofesod.dto.CreateUserRequest;
import io.github.cunyu1943.demofesod.dto.Result;
import io.github.cunyu1943.demofesod.dto.UserDTO;
import io.github.cunyu1943.demofesod.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @description: 用户控制器单元测试
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
    private CreateUserRequest createRequest;

    @BeforeEach
    void setUp() {
        mockUserDTO = UserDTO.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        createRequest = new CreateUserRequest("newuser", "new@example.com", "password123");
    }

    @Test
    @DisplayName("创建用户 - 成功")
    void createUser_Success() {
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(Result.success(mockUserDTO));

        ResponseEntity<Result<UserDTO>> response = userController.createUser(createRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(200, response.getBody().getRespCode());
        assertNotNull(response.getBody().getData());
    }

    @Test
    @DisplayName("创建用户 - 用户名已存在")
    void createUser_UsernameExists() {
        when(userService.createUser(any(CreateUserRequest.class)))
                .thenReturn(Result.fail(400, "用户名已存在"));

        ResponseEntity<Result<UserDTO>> response = userController.createUser(createRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(400, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("查询所有用户 - 成功")
    void getAllUsers_Success() {
        List<UserDTO> users = Arrays.asList(mockUserDTO);
        when(userService.getAllUsers()).thenReturn(Result.success(users));

        ResponseEntity<Result<List<UserDTO>>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getRespCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    @DisplayName("根据ID查询用户 - 成功")
    void getUserById_Success() {
        when(userService.getUserById(1L)).thenReturn(Result.success(mockUserDTO));

        ResponseEntity<Result<UserDTO>> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getRespCode());
        assertEquals("testuser", response.getBody().getData().getUsername());
    }

    @Test
    @DisplayName("根据ID查询用户 - 用户不存在")
    void getUserById_NotFound() {
        when(userService.getUserById(999L)).thenReturn(Result.fail(404, "用户不存在"));

        ResponseEntity<Result<UserDTO>> response = userController.getUserById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("删除用户 - 成功")
    void deleteUser_Success() {
        when(userService.deleteUser(1L)).thenReturn(Result.success());

        ResponseEntity<Result<Void>> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("删除用户 - 用户不存在")
    void deleteUser_NotFound() {
        when(userService.deleteUser(999L)).thenReturn(Result.fail(404, "用户不存在"));

        ResponseEntity<Result<Void>> response = userController.deleteUser(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("导出用户 - 成功")
    void exportUsersToExcel_Success() {
        String filePath = "/tmp/users.xlsx";
        when(userService.exportUsersToExcel(filePath)).thenReturn(Result.success(filePath));

        ResponseEntity<Result<String>> response = userController.exportUsersToExcel(filePath);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("导出用户 - 失败")
    void exportUsersToExcel_Failure() {
        String filePath = "/invalid/path/users.xlsx";
        when(userService.exportUsersToExcel(filePath)).thenReturn(Result.fail("导出失败"));

        ResponseEntity<Result<String>> response = userController.exportUsersToExcel(filePath);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getRespCode());
    }

    @Test
    @DisplayName("导入用户 - 成功")
    void importUsersFromExcel_Success() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("users.xlsx");
        when(userService.importUsersFromExcel(any(String.class))).thenReturn(Result.success(5));

        ResponseEntity<Result<Integer>> response = userController.importUsersFromExcel(mockFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getRespCode());
        assertEquals(5, response.getBody().getData());
    }

    @Test
    @DisplayName("导入用户 - 文件上传失败")
    void importUsersFromExcel_UploadFailure() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("users.xlsx");
        doThrow(new IOException("File upload failed")).when(mockFile).transferTo(any(File.class));

        ResponseEntity<Result<Integer>> response = userController.importUsersFromExcel(mockFile);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getRespCode());
        assertTrue(response.getBody().getRespMsg().contains("文件上传失败"));
    }
}
