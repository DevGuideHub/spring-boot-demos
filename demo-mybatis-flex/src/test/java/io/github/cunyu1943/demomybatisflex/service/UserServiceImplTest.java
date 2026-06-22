package io.github.cunyu1943.demomybatisflex.service;

import com.mybatisflex.core.query.QueryWrapper;
import io.github.cunyu1943.demomybatisflex.entity.User;
import io.github.cunyu1943.demomybatisflex.mapper.UserMapper;
import io.github.cunyu1943.demomybatisflex.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @description: 用户服务实现类单元测试
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: UserServiceImplTest
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userMapper);
        user = User.builder()
                .id(1L)
                .name("张三")
                .age(25)
                .email("zhangsan@example.com")
                .build();
    }

    @Test
    @DisplayName("保存用户 - 新增成功")
    void save_CreateSuccess() {
        User newUser = User.builder()
                .name("李四")
                .age(30)
                .email("lisi@example.com")
                .build();

        when(userMapper.selectCountByQuery(any(QueryWrapper.class))).thenReturn(0L);
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(2L);
            return 1;
        }).when(userMapper).insert(any(User.class));

        User savedUser = userService.save(newUser);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(2L, savedUser.getId());
        verify(userMapper, times(1)).insert(newUser);
    }

    @Test
    @DisplayName("保存用户 - 更新成功")
    void save_UpdateSuccess() {
        when(userMapper.selectCountByQuery(any(QueryWrapper.class))).thenReturn(0L);

        User updatedUser = userService.save(user);

        assertNotNull(updatedUser);
        verify(userMapper, times(1)).update(user);
    }

    @Test
    @DisplayName("保存用户 - 用户对象为空")
    void save_NullUser() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.save(null));
        assertEquals("用户对象不能为空", exception.getMessage());
    }

    @Test
    @DisplayName("保存用户 - 邮箱已存在")
    void save_EmailExists() {
        when(userMapper.selectCountByQuery(any(QueryWrapper.class))).thenReturn(1L);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.save(user));
        assertEquals("邮箱已存在", exception.getMessage());
    }

    @Test
    @DisplayName("根据ID查询用户 - 成功")
    void findById_Success() {
        when(userMapper.selectOneById(1L)).thenReturn(user);

        Optional<User> foundUser = userService.findById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("张三", foundUser.get().getName());
    }

    @Test
    @DisplayName("根据ID查询用户 - 不存在")
    void findById_NotFound() {
        when(userMapper.selectOneById(99L)).thenReturn(null);

        Optional<User> foundUser = userService.findById(99L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("根据ID查询用户 - 无效ID")
    void findById_InvalidId() {
        Optional<User> foundUser = userService.findById(null);
        assertFalse(foundUser.isPresent());

        foundUser = userService.findById(0L);
        assertFalse(foundUser.isPresent());

        foundUser = userService.findById(-1L);
        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("查询所有用户 - 成功")
    void findAll_Success() {
        List<User> users = Arrays.asList(user);
        when(userMapper.selectListByQuery(any(QueryWrapper.class))).thenReturn(users);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("张三", result.get(0).getName());
    }

    @Test
    @DisplayName("查询所有用户 - 空列表")
    void findAll_EmptyList() {
        when(userMapper.selectListByQuery(any(QueryWrapper.class))).thenReturn(List.of());

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("根据ID删除用户 - 成功")
    void deleteById_Success() {
        when(userMapper.deleteById(1L)).thenReturn(1);

        boolean deleted = userService.deleteById(1L);

        assertTrue(deleted);
        verify(userMapper, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("根据ID删除用户 - 不存在")
    void deleteById_NotFound() {
        when(userMapper.deleteById(99L)).thenReturn(0);

        boolean deleted = userService.deleteById(99L);

        assertFalse(deleted);
    }

    @Test
    @DisplayName("根据ID删除用户 - 无效ID")
    void deleteById_InvalidId() {
        boolean deleted = userService.deleteById(null);
        assertFalse(deleted);

        deleted = userService.deleteById(0L);
        assertFalse(deleted);

        deleted = userService.deleteById(-1L);
        assertFalse(deleted);
    }

    @Test
    @DisplayName("根据姓名查询用户 - 成功")
    void findByName_Success() {
        List<User> users = Arrays.asList(user);
        when(userMapper.selectListByQuery(any(QueryWrapper.class))).thenReturn(users);

        List<User> result = userService.findByName("张三");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("张三", result.get(0).getName());
    }

    @Test
    @DisplayName("根据姓名查询用户 - 空列表")
    void findByName_EmptyList() {
        when(userMapper.selectListByQuery(any(QueryWrapper.class))).thenReturn(List.of());

        List<User> result = userService.findByName("不存在");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("根据姓名查询用户 - 空姓名")
    void findByName_EmptyName() {
        List<User> result = userService.findByName(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());

        result = userService.findByName("");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("根据邮箱查询用户 - 成功")
    void findByEmail_Success() {
        when(userMapper.selectOneByQuery(any(QueryWrapper.class))).thenReturn(user);

        Optional<User> foundUser = userService.findByEmail("zhangsan@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("zhangsan@example.com", foundUser.get().getEmail());
    }

    @Test
    @DisplayName("根据邮箱查询用户 - 不存在")
    void findByEmail_NotFound() {
        when(userMapper.selectOneByQuery(any(QueryWrapper.class))).thenReturn(null);

        Optional<User> foundUser = userService.findByEmail("notfound@example.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("根据邮箱查询用户 - 空邮箱")
    void findByEmail_EmptyEmail() {
        Optional<User> foundUser = userService.findByEmail(null);
        assertFalse(foundUser.isPresent());

        foundUser = userService.findByEmail("");
        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("根据年龄范围查询用户 - 成功")
    void findByAgeBetween_Success() {
        List<User> users = Arrays.asList(user);
        when(userMapper.selectListByQuery(any(QueryWrapper.class))).thenReturn(users);

        List<User> result = userService.findByAgeBetween(20, 30);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(25, result.get(0).getAge());
    }

    @Test
    @DisplayName("根据年龄范围查询用户 - 空列表")
    void findByAgeBetween_EmptyList() {
        when(userMapper.selectListByQuery(any(QueryWrapper.class))).thenReturn(List.of());

        List<User> result = userService.findByAgeBetween(100, 200);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("根据年龄范围查询用户 - 无效参数")
    void findByAgeBetween_InvalidParams() {
        List<User> result = userService.findByAgeBetween(null, 30);
        assertNotNull(result);
        assertTrue(result.isEmpty());

        result = userService.findByAgeBetween(20, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());

        result = userService.findByAgeBetween(30, 20);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("检查邮箱是否存在 - 存在")
    void existsByEmail_Exists() {
        when(userMapper.selectCountByQuery(any(QueryWrapper.class))).thenReturn(1L);

        boolean exists = userService.existsByEmail("zhangsan@example.com");

        assertTrue(exists);
    }

    @Test
    @DisplayName("检查邮箱是否存在 - 不存在")
    void existsByEmail_NotExists() {
        when(userMapper.selectCountByQuery(any(QueryWrapper.class))).thenReturn(0L);

        boolean exists = userService.existsByEmail("notfound@example.com");

        assertFalse(exists);
    }

    @Test
    @DisplayName("检查邮箱是否存在 - 空邮箱")
    void existsByEmail_EmptyEmail() {
        boolean exists = userService.existsByEmail(null);
        assertFalse(exists);

        exists = userService.existsByEmail("");
        assertFalse(exists);
    }
}
