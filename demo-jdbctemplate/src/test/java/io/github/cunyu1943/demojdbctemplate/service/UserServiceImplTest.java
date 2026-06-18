package io.github.cunyu1943.demojdbctemplate.service;

import io.github.cunyu1943.demojdbctemplate.entity.User;
import io.github.cunyu1943.demojdbctemplate.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @description: UserServiceImpl 单元测试
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(jdbcTemplate);
        testUser = new User(1L, "张三", 25, "zhangsan@example.com");
    }

    @Test
    void testFindAll_ShouldReturnUserList() {
        List<User> users = Arrays.asList(testUser, new User(2L, "李四", 30, "lisi@example.com"));
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void testFindAll_WhenEmpty_ShouldReturnEmptyList() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Arrays.asList());

        List<User> result = userService.findAll();

        assertTrue(result.isEmpty());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void testFindById_ShouldReturnUser() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1L))).thenReturn(testUser);

        User result = userService.findById(1L);

        assertNotNull(result);
        assertEquals("张三", result.getName());
        assertEquals(25, result.getAge());
        assertEquals("zhangsan@example.com", result.getEmail());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), eq(1L));
    }

    @Test
    void testFindById_WithNegativeId_ShouldHandle() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(-1L))).thenReturn(null);

        User result = userService.findById(-1L);

        assertNull(result);
    }

    @Test
    void testFindById_WhenUserNotFound_ShouldReturnNull() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(999L))).thenReturn(null);

        User result = userService.findById(999L);

        assertNull(result);
    }

    @Test
    void testInsert_ShouldReturnPositiveResult() {
        when(jdbcTemplate.update(anyString(), eq("张三"), eq(25), eq("zhangsan@example.com"))).thenReturn(1);

        int result = userService.insert(testUser);

        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), eq("张三"), eq(25), eq("zhangsan@example.com"));
    }

    @Test
    void testInsert_WithNullEmail_ShouldWork() {
        testUser.setEmail(null);
        when(jdbcTemplate.update(anyString(), eq("张三"), eq(25), isNull())).thenReturn(1);

        int result = userService.insert(testUser);

        assertEquals(1, result);
    }

    @Test
    void testInsert_WithEmptyName_ShouldWork() {
        testUser.setName("");
        when(jdbcTemplate.update(anyString(), eq(""), eq(25), eq("zhangsan@example.com"))).thenReturn(1);

        int result = userService.insert(testUser);

        assertEquals(1, result);
    }

    @Test
    void testInsert_WithZeroAge_ShouldWork() {
        testUser.setAge(0);
        when(jdbcTemplate.update(anyString(), eq("张三"), eq(0), eq("zhangsan@example.com"))).thenReturn(1);

        int result = userService.insert(testUser);

        assertEquals(1, result);
    }

    @Test
    void testInsert_WhenFail_ShouldReturnZero() {
        when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(0);

        int result = userService.insert(testUser);

        assertEquals(0, result);
    }

    @Test
    void testUpdate_ShouldReturnPositiveResult() {
        when(jdbcTemplate.update(anyString(), eq("张三"), eq(25), eq("zhangsan@example.com"), eq(1L))).thenReturn(1);

        int result = userService.update(testUser);

        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), eq("张三"), eq(25), eq("zhangsan@example.com"), eq(1L));
    }

    @Test
    void testUpdate_WhenNoRowAffected_ShouldReturnZero() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any())).thenReturn(0);

        int result = userService.update(testUser);

        assertEquals(0, result);
    }

    @Test
    void testUpdate_WithNullFields_ShouldWork() {
        testUser.setEmail(null);
        testUser.setAge(null);
        when(jdbcTemplate.update(anyString(), eq("张三"), isNull(), isNull(), eq(1L))).thenReturn(1);

        int result = userService.update(testUser);

        assertEquals(1, result);
    }

    @Test
    void testDeleteById_ShouldReturnPositiveResult() {
        when(jdbcTemplate.update(anyString(), eq(1L))).thenReturn(1);

        int result = userService.deleteById(1L);

        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(1L));
    }

    @Test
    void testDeleteById_WhenNoRowAffected_ShouldReturnZero() {
        when(jdbcTemplate.update(anyString(), eq(999L))).thenReturn(0);

        int result = userService.deleteById(999L);

        assertEquals(0, result);
    }

    @Test
    void testDeleteById_WithZeroId_ShouldHandle() {
        when(jdbcTemplate.update(anyString(), eq(0L))).thenReturn(0);

        int result = userService.deleteById(0L);

        assertEquals(0, result);
    }

    @Test
    void testDeleteById_WithNegativeId_ShouldHandle() {
        when(jdbcTemplate.update(anyString(), eq(-1L))).thenReturn(0);

        int result = userService.deleteById(-1L);

        assertEquals(0, result);
    }

    @Test
    void testCount_ShouldReturnCount() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(10);

        int result = userService.count();

        assertEquals(10, result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class));
    }

    @Test
    void testCount_WhenEmptyTable_ShouldReturnZero() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(0);

        int result = userService.count();

        assertEquals(0, result);
    }

    @Test
    void testServiceInstantiation() {
        assertNotNull(userService);
    }

    @Test
    void testRowMapper_MapsCorrectly() throws SQLException {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenAnswer(invocation -> {
            RowMapper<User> rowMapper = invocation.getArgument(1);
            List<User> users = Arrays.asList(testUser);
            return users;
        });

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("张三", result.get(0).getName());
    }
}