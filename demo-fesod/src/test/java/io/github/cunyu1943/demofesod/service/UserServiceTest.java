package io.github.cunyu1943.demofesod.service;

import io.github.cunyu1943.demofesod.dto.CreateUserRequest;
import io.github.cunyu1943.demofesod.dto.Result;
import io.github.cunyu1943.demofesod.dto.UserDTO;
import io.github.cunyu1943.demofesod.entity.User;
import io.github.cunyu1943.demofesod.repository.UserRepository;
import io.github.cunyu1943.demofesod.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @description: 用户服务单元测试
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

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;
    private CreateUserRequest createRequest;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("$2a$10$encodedPassword");
        mockUser.setCreatedAt(LocalDateTime.now());
        mockUser.setUpdatedAt(LocalDateTime.now());

        createRequest = new CreateUserRequest("newuser", "new@example.com", "password123");
    }

    @Test
    @DisplayName("创建用户 - 成功")
    void createUser_Success() {
        when(userRepository.existsByUsername(createRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(createRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        Result<UserDTO> result = userService.createUser(createRequest);

        assertEquals(200, result.getRespCode());
        assertEquals("操作成功", result.getRespMsg());
        assertNotNull(result.getData());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("创建用户 - 用户名已存在")
    void createUser_UsernameExists() {
        when(userRepository.existsByUsername(createRequest.getUsername())).thenReturn(true);

        Result<UserDTO> result = userService.createUser(createRequest);

        assertEquals(400, result.getRespCode());
        assertEquals("用户名已存在", result.getRespMsg());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("创建用户 - 邮箱已存在")
    void createUser_EmailExists() {
        when(userRepository.existsByUsername(createRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(createRequest.getEmail())).thenReturn(true);

        Result<UserDTO> result = userService.createUser(createRequest);

        assertEquals(400, result.getRespCode());
        assertEquals("邮箱已存在", result.getRespMsg());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("查询所有用户 - 成功")
    void getAllUsers_Success() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        Result<List<UserDTO>> result = userService.getAllUsers();

        assertEquals(200, result.getRespCode());
        assertEquals(2, result.getData().size());
    }

    @Test
    @DisplayName("查询所有用户 - 空列表")
    void getAllUsers_Empty() {
        when(userRepository.findAll()).thenReturn(List.of());

        Result<List<UserDTO>> result = userService.getAllUsers();

        assertEquals(200, result.getRespCode());
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
    @DisplayName("根据ID查询用户 - 用户不存在")
    void getUserById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Result<UserDTO> result = userService.getUserById(999L);

        assertEquals(404, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
    }

    @Test
    @DisplayName("删除用户 - 成功")
    void deleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        Result<Void> result = userService.deleteUser(1L);

        assertEquals(200, result.getRespCode());
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("删除用户 - 用户不存在")
    void deleteUser_NotFound() {
        when(userRepository.existsById(999L)).thenReturn(false);

        Result<Void> result = userService.deleteUser(999L);

        assertEquals(404, result.getRespCode());
        assertEquals("用户不存在", result.getRespMsg());
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("导出用户到Excel - 失败")
    void exportUsersToExcel_Failure() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("数据库错误"));

        Result<String> result = userService.exportUsersToExcel("/tmp/test.xlsx");

        assertEquals(500, result.getRespCode());
        assertTrue(result.getRespMsg().contains("导出失败"));
    }

    @Test
    @DisplayName("导入用户从Excel - 失败")
    void importUsersFromExcel_Failure() {
        // FesodSheet.read会先尝试读取文件，文件不存在会抛出异常
        // 这个测试覆盖异常处理路径
        Result<Integer> result = userService.importUsersFromExcel("/nonexistent/file.xlsx");

        assertEquals(500, result.getRespCode());
        assertTrue(result.getRespMsg().contains("导入失败"));
    }
}
