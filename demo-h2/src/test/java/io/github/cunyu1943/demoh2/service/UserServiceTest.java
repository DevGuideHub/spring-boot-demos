
package io.github.cunyu1943.demoh2.service;

import io.github.cunyu1943.demoh2.dto.CreateUserRequest;
import io.github.cunyu1943.demoh2.dto.Result;
import io.github.cunyu1943.demoh2.dto.UpdateUserRequest;
import io.github.cunyu1943.demoh2.dto.UserDTO;
import io.github.cunyu1943.demoh2.entity.User;
import io.github.cunyu1943.demoh2.repository.UserRepository;
import io.github.cunyu1943.demoh2.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @description: 用户服务测试
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: UserServiceTest
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setCreatedAt(LocalDateTime.now());
        mockUser.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("查询所有用户 - 成功")
    void listUsers_Success() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser));

        Result<List<UserDTO>> result = userService.listUsers();

        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertNotNull(result.getData());
        assertEquals(1, result.getData().size());
        assertEquals("testuser", result.getData().get(0).getUsername());
    }

    @Test
    @DisplayName("查询所有用户 - 空列表")
    void listUsers_EmptyList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        Result<List<UserDTO>> result = userService.listUsers();

        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertTrue(result.getData().isEmpty());
    }

    @Test
    @DisplayName("根据ID查询用户 - 成功")
    void getUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        Result<UserDTO> result = userService.getUserById(1L);

        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("testuser", result.getData().getUsername());
    }

    @Test
    @DisplayName("根据ID查询用户 - 不存在")
    void getUserById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Result<UserDTO> result = userService.getUserById(99L);

        assertEquals(404, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("创建用户 - 成功")
    void createUser_Success() {
        CreateUserRequest request = new CreateUserRequest("newuser", "new@example.com", "password123");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        Result<UserDTO> result = userService.createUser(request);

        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
        assertEquals("testuser", result.getData().getUsername());
    }

    @Test
    @DisplayName("创建用户 - 用户名已存在")
    void createUser_UsernameExists() {
        CreateUserRequest request = new CreateUserRequest("testuser", "new@example.com", "password123");

        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        Result<UserDTO> result = userService.createUser(request);

        assertEquals(400, result.getRespCode());
        assertEquals("用户名已存在", result.getRespMsg());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("创建用户 - 邮箱已存在")
    void createUser_EmailExists() {
        CreateUserRequest request = new CreateUserRequest("newuser", "test@example.com", "password123");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        Result<UserDTO> result = userService.createUser(request);

        assertEquals(400, result.getRespCode());
        assertEquals("邮箱已存在", result.getRespMsg());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("更新用户 - 成功")
    void updateUser_Success() {
        UpdateUserRequest request = new UpdateUserRequest("updateduser", null, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.existsByUsername("updateduser")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        Result<UserDTO> result = userService.updateUser(1L, request);

        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("更新用户 - 用户不存在")
    void updateUser_NotFound() {
        UpdateUserRequest request = new UpdateUserRequest("updateduser", null, null);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Result<UserDTO> result = userService.updateUser(99L, request);

        assertEquals(404, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
    }

    @Test
    @DisplayName("更新用户 - 用户名已存在")
    void updateUser_UsernameExists() {
        UpdateUserRequest request = new UpdateUserRequest("existinguser", null, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        Result<UserDTO> result = userService.updateUser(1L, request);

        assertEquals(400, result.getRespCode());
        assertEquals("用户名已存在", result.getRespMsg());
    }

    @Test
    @DisplayName("更新用户 - 更新邮箱")
    void updateUser_UpdateEmail() {
        UpdateUserRequest request = new UpdateUserRequest(null, "newemail@example.com", null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.existsByEmail("newemail@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        Result<UserDTO> result = userService.updateUser(1L, request);

        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("更新用户 - 更新密码")
    void updateUser_UpdatePassword() {
        UpdateUserRequest request = new UpdateUserRequest(null, null, "newpassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.encode("newpassword")).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        Result<UserDTO> result = userService.updateUser(1L, request);

        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
    }

    @Test
    @DisplayName("删除用户 - 成功")
    void deleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        Result<Void> result = userService.deleteUser(1L);

        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
    }

    @Test
    @DisplayName("删除用户 - 用户不存在")
    void deleteUser_NotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        Result<Void> result = userService.deleteUser(99L);

        assertEquals(404, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
    }

    @Test
    @DisplayName("创建用户 - 空请求")
    void createUser_NullRequest() {
        assertThrows(NullPointerException.class, () -> userService.createUser(null));
    }

    @Test
    @DisplayName("更新用户 - 空请求")
    void updateUser_NullRequest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        Result<UserDTO> result = userService.updateUser(1L, null);

        assertEquals(200, result.getRespCode());
        assertNotNull(result.getData());
    }
}
