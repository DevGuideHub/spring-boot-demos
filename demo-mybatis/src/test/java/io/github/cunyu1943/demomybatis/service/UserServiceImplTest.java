package io.github.cunyu1943.demomybatis.service;

import io.github.cunyu1943.demomybatis.config.ResourceNotFoundException;
import io.github.cunyu1943.demomybatis.entity.User;
import io.github.cunyu1943.demomybatis.mapper.UserMapper;
import io.github.cunyu1943.demomybatis.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @description: UserService 实现类测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-22
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .userName("测试用户")
                .age(25)
                .email("test@example.com")
                .build();
    }

    @Test
    void testSave_NewUser() {
        User newUser = User.builder()
                .userName("新用户")
                .age(30)
                .email("new@example.com")
                .build();

        when(userMapper.insert(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return 1;
        });

        User savedUser = userService.save(newUser);

        assertNotNull(savedUser.getId());
        verify(userMapper, times(1)).insert(any(User.class));
    }

    @Test
    void testSave_ExistingUser() {
        when(userMapper.updateById(any(User.class))).thenReturn(1);

        User result = userService.save(testUser);

        assertNotNull(result);
        verify(userMapper, times(1)).updateById(any(User.class));
    }

    @Test
    void testSave_NullUser() {
        assertThrows(IllegalArgumentException.class, () -> userService.save(null));
    }

    @Test
    void testSave_EmptyName() {
        User user = User.builder().userName("").build();
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    void testSave_NullName() {
        User user = User.builder().userName(null).build();
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    void testFindById_Success() {
        when(userMapper.selectById(1L)).thenReturn(testUser);

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testUser.getUserName(), result.get().getUserName());
        verify(userMapper, times(1)).selectById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(userMapper.selectById(999L)).thenReturn(null);

        Optional<User> result = userService.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindById_NegativeId() {
        assertThrows(IllegalArgumentException.class, () -> userService.findById(-1L));
    }

    @Test
    void testFindById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> userService.findById(null));
    }

    @Test
    void testFindById_ZeroId() {
        assertThrows(IllegalArgumentException.class, () -> userService.findById(0L));
    }

    @Test
    void testFindAll() {
        when(userMapper.selectAll()).thenReturn(Arrays.asList(testUser));

        List<User> users = userService.findAll();

        assertNotNull(users);
        assertFalse(users.isEmpty());
        verify(userMapper, times(1)).selectAll();
    }

    @Test
    void testFindAll_Empty() {
        when(userMapper.selectAll()).thenReturn(Collections.emptyList());

        List<User> users = userService.findAll();

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void testDeleteById_Success() {
        when(userMapper.selectById(1L)).thenReturn(testUser);
        when(userMapper.deleteById(1L)).thenReturn(1);

        assertDoesNotThrow(() -> userService.deleteById(1L));
        verify(userMapper, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NegativeId() {
        assertThrows(IllegalArgumentException.class, () -> userService.deleteById(-1L));
    }

    @Test
    void testDeleteById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> userService.deleteById(null));
    }

    @Test
    void testDeleteById_ZeroId() {
        assertThrows(IllegalArgumentException.class, () -> userService.deleteById(0L));
    }

    @Test
    void testDeleteById_NotFound() {
        when(userMapper.selectById(999L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteById(999L));
    }

    @Test
    void testFindByName_Success() {
        when(userMapper.selectByName("测试用户")).thenReturn(Arrays.asList(testUser));

        List<User> users = userService.findByName("测试用户");

        assertNotNull(users);
        assertFalse(users.isEmpty());
        verify(userMapper, times(1)).selectByName("测试用户");
    }

    @Test
    void testFindByName_EmptyName() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByName(""));
    }

    @Test
    void testFindByName_NullName() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByName(null));
    }

    @Test
    void testFindByNameContaining_Success() {
        when(userMapper.selectByNameContaining("测试")).thenReturn(Arrays.asList(testUser));

        List<User> users = userService.findByNameContaining("测试");

        assertNotNull(users);
        assertFalse(users.isEmpty());
        verify(userMapper, times(1)).selectByNameContaining("测试");
    }

    @Test
    void testFindByNameContaining_EmptyName() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByNameContaining(""));
    }

    @Test
    void testFindByNameContaining_NullName() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByNameContaining(null));
    }

    @Test
    void testFindByEmail_Success() {
        when(userMapper.selectByEmail("test@example.com")).thenReturn(testUser);

        Optional<User> result = userService.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(testUser.getUserName(), result.get().getUserName());
        verify(userMapper, times(1)).selectByEmail("test@example.com");
    }

    @Test
    void testFindByEmail_NotFound() {
        when(userMapper.selectByEmail("notexist@example.com")).thenReturn(null);

        Optional<User> result = userService.findByEmail("notexist@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    void testFindByEmail_EmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByEmail(""));
    }

    @Test
    void testFindByEmail_NullEmail() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByEmail(null));
    }

    @Test
    void testFindByAgeBetween_Success() {
        when(userMapper.selectByAgeBetween(20, 30)).thenReturn(Arrays.asList(testUser));

        List<User> users = userService.findByAgeBetween(20, 30);

        assertNotNull(users);
        assertFalse(users.isEmpty());
        verify(userMapper, times(1)).selectByAgeBetween(20, 30);
    }

    @Test
    void testFindByAgeBetween_MinGreaterThanMax() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByAgeBetween(30, 20));
    }

    @Test
    void testFindByAgeBetween_NullMinAge() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByAgeBetween(null, 30));
    }

    @Test
    void testFindByAgeBetween_NullMaxAge() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByAgeBetween(20, null));
    }

    @Test
    void testFindByAgeBetween_BothNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByAgeBetween(null, null));
    }

    @Test
    void testExistsByEmail_Exists() {
        when(userMapper.countByEmail("test@example.com")).thenReturn(1);

        boolean exists = userService.existsByEmail("test@example.com");

        assertTrue(exists);
        verify(userMapper, times(1)).countByEmail("test@example.com");
    }

    @Test
    void testExistsByEmail_NotExists() {
        when(userMapper.countByEmail("notexist@example.com")).thenReturn(0);

        boolean exists = userService.existsByEmail("notexist@example.com");

        assertFalse(exists);
    }

    @Test
    void testExistsByEmail_EmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> userService.existsByEmail(""));
    }

    @Test
    void testExistsByEmail_NullEmail() {
        assertThrows(IllegalArgumentException.class, () -> userService.existsByEmail(null));
    }

    @Test
    void testUpdate_Success() {
        when(userMapper.selectById(1L)).thenReturn(testUser);
        when(userMapper.updateById(any(User.class))).thenReturn(1);

        User updateUser = User.builder().userName("新名称").build();
        User result = userService.update(1L, updateUser);

        assertNotNull(result);
        assertEquals("新名称", result.getUserName());
        verify(userMapper, times(1)).selectById(1L);
        verify(userMapper, times(1)).updateById(any(User.class));
    }

    @Test
    void testUpdate_Success_WithAllFields() {
        when(userMapper.selectById(1L)).thenReturn(testUser);
        when(userMapper.updateById(any(User.class))).thenReturn(1);

        User updateUser = User.builder()
                .userName("新名称")
                .age(30)
                .email("new@example.com")
                .build();
        User result = userService.update(1L, updateUser);

        assertNotNull(result);
        assertEquals("新名称", result.getUserName());
        assertEquals(30, result.getAge());
        assertEquals("new@example.com", result.getEmail());
    }

    @Test
    void testUpdate_NotFound() {
        when(userMapper.selectById(999L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.update(999L, testUser));
    }

    @Test
    void testUpdate_NullId() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(null, testUser));
    }

    @Test
    void testUpdate_NegativeId() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(-1L, testUser));
    }

    @Test
    void testUpdate_ZeroId() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(0L, testUser));
    }

    @Test
    void testUpdate_NullUser() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(1L, null));
    }

    @Test
    void testUpdate_EmptyUserNameIgnored() {
        when(userMapper.selectById(1L)).thenReturn(testUser);
        when(userMapper.updateById(any(User.class))).thenReturn(1);

        User updateUser = User.builder().userName("").build();
        User result = userService.update(1L, updateUser);

        assertNotNull(result);
        assertEquals("测试用户", result.getUserName());
    }

    @Test
    void testUpdate_NullFieldsIgnored() {
        when(userMapper.selectById(1L)).thenReturn(testUser);
        when(userMapper.updateById(any(User.class))).thenReturn(1);

        User updateUser = User.builder().userName("新名称").age(null).email(null).build();
        User result = userService.update(1L, updateUser);

        assertNotNull(result);
        assertEquals("新名称", result.getUserName());
        assertEquals(25, result.getAge());
        assertEquals("test@example.com", result.getEmail());
    }
}