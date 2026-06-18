package io.github.cunyu1943.demojpa.service;

import io.github.cunyu1943.demojpa.entity.User;
import io.github.cunyu1943.demojpa.repository.UserRepository;
import io.github.cunyu1943.demojpa.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

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
    void testSave_NormalCase_ShouldReturnUser() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.save(testUser);

        assertNotNull(result);
        assertEquals("张三", result.getName());
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSave_NullUser_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> userService.save(null));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testSave_EmailExists_ShouldThrowException() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.save(testUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testSave_NullEmail_ShouldWork() {
        testUser.setEmail(null);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.save(testUser);

        assertNotNull(result);
        assertNull(result.getEmail());
        verify(userRepository, never()).existsByEmail(any());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindById_NormalCase_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("张三", result.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NullId_ShouldReturnEmpty() {
        Optional<User> result = userService.findById(null);

        assertFalse(result.isPresent());
        verify(userRepository, never()).findById(any());
    }

    @Test
    void testFindById_NegativeId_ShouldReturnEmpty() {
        Optional<User> result = userService.findById(-1L);

        assertFalse(result.isPresent());
        verify(userRepository, never()).findById(any());
    }

    @Test
    void testFindById_ZeroId_ShouldReturnEmpty() {
        Optional<User> result = userService.findById(0L);

        assertFalse(result.isPresent());
        verify(userRepository, never()).findById(any());
    }

    @Test
    void testFindById_UserNotFound_ShouldReturnEmpty() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(1L);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll_ShouldReturnUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindAll_EmptyList_ShouldReturnEmpty() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById_NormalCase_ShouldDelete() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteById(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NullId_ShouldDoNothing() {
        userService.deleteById(null);

        verify(userRepository, never()).existsById(any());
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteById_NegativeId_ShouldDoNothing() {
        userService.deleteById(-1L);

        verify(userRepository, never()).existsById(any());
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteById_UserNotFound_ShouldDoNothing() {
        when(userRepository.existsById(1L)).thenReturn(false);

        userService.deleteById(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void testFindByName_NormalCase_ShouldReturnUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findByName("张三")).thenReturn(users);

        List<User> result = userService.findByName("张三");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByName("张三");
    }

    @Test
    void testFindByName_NullName_ShouldReturnEmpty() {
        List<User> result = userService.findByName(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, never()).findByName(any());
    }

    @Test
    void testFindByName_EmptyName_ShouldReturnEmpty() {
        List<User> result = userService.findByName("");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, never()).findByName(any());
    }

    @Test
    void testFindByName_UserNotFound_ShouldReturnEmpty() {
        when(userRepository.findByName("李四")).thenReturn(List.of());

        List<User> result = userService.findByName("李四");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByName("李四");
    }

    @Test
    void testFindByNameContaining_NormalCase_ShouldReturnUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findByNameContaining("张")).thenReturn(users);

        List<User> result = userService.findByNameContaining("张");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByNameContaining("张");
    }

    @Test
    void testFindByNameContaining_NullName_ShouldReturnEmpty() {
        List<User> result = userService.findByNameContaining(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, never()).findByNameContaining(any());
    }

    @Test
    void testFindByNameContaining_EmptyName_ShouldReturnEmpty() {
        List<User> result = userService.findByNameContaining("");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, never()).findByNameContaining(any());
    }

    @Test
    void testFindByEmail_NormalCase_ShouldReturnUser() {
        when(userRepository.findByEmail("zhangsan@example.com")).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findByEmail("zhangsan@example.com");

        assertTrue(result.isPresent());
        assertEquals("张三", result.get().getName());
        verify(userRepository, times(1)).findByEmail("zhangsan@example.com");
    }

    @Test
    void testFindByEmail_NullEmail_ShouldReturnEmpty() {
        Optional<User> result = userService.findByEmail(null);

        assertFalse(result.isPresent());
        verify(userRepository, never()).findByEmail(any());
    }

    @Test
    void testFindByEmail_EmptyEmail_ShouldReturnEmpty() {
        Optional<User> result = userService.findByEmail("");

        assertFalse(result.isPresent());
        verify(userRepository, never()).findByEmail(any());
    }

    @Test
    void testFindByEmail_UserNotFound_ShouldReturnEmpty() {
        when(userRepository.findByEmail("notexist@example.com")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByEmail("notexist@example.com");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail("notexist@example.com");
    }

    @Test
    void testFindByAgeBetween_NormalCase_ShouldReturnUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findByAgeBetween(20, 30)).thenReturn(users);

        List<User> result = userService.findByAgeBetween(20, 30);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByAgeBetween(20, 30);
    }

    @Test
    void testFindByAgeBetween_NullMinAge_ShouldReturnEmpty() {
        List<User> result = userService.findByAgeBetween(null, 30);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, never()).findByAgeBetween(any(), any());
    }

    @Test
    void testFindByAgeBetween_NullMaxAge_ShouldReturnEmpty() {
        List<User> result = userService.findByAgeBetween(20, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, never()).findByAgeBetween(any(), any());
    }

    @Test
    void testFindByAgeBetween_MinGreaterThanMax_ShouldReturnEmpty() {
        List<User> result = userService.findByAgeBetween(30, 20);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, never()).findByAgeBetween(any(), any());
    }

    @Test
    void testExistsByEmail_NormalCase_ShouldReturnTrue() {
        when(userRepository.existsByEmail("zhangsan@example.com")).thenReturn(true);

        boolean result = userService.existsByEmail("zhangsan@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail("zhangsan@example.com");
    }

    @Test
    void testExistsByEmail_EmailNotExists_ShouldReturnFalse() {
        when(userRepository.existsByEmail("notexist@example.com")).thenReturn(false);

        boolean result = userService.existsByEmail("notexist@example.com");

        assertFalse(result);
        verify(userRepository, times(1)).existsByEmail("notexist@example.com");
    }

    @Test
    void testExistsByEmail_NullEmail_ShouldReturnFalse() {
        boolean result = userService.existsByEmail(null);

        assertFalse(result);
        verify(userRepository, never()).existsByEmail(any());
    }

    @Test
    void testExistsByEmail_EmptyEmail_ShouldReturnFalse() {
        boolean result = userService.existsByEmail("");

        assertFalse(result);
        verify(userRepository, never()).existsByEmail(any());
    }

    @Test
    void testUpdate_NormalCase_ShouldReturnUpdatedUser() {
        User updateUser = User.builder()
                .name("李四")
                .age(30)
                .email("lisi@example.com")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.update(1L, updateUser);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdate_NullId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(null, testUser));
        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdate_NegativeId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(-1L, testUser));
        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdate_NullUser_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(1L, null));
        verify(userRepository, never()).findById(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdate_UserNotFound_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.update(1L, testUser));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdate_PartialUpdate_ShouldWork() {
        User partialUser = User.builder()
                .name("李四")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.update(1L, partialUser);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testServiceInstantiation() {
        assertNotNull(userService);
    }

    @Test
    void testUserBuilder_ShouldBuildCorrectly() {
        User user = User.builder()
                .id(2L)
                .name("李四")
                .age(30)
                .email("lisi@example.com")
                .build();

        assertNotNull(user);
        assertEquals(2L, user.getId());
        assertEquals("李四", user.getName());
        assertEquals(30, user.getAge());
        assertEquals("lisi@example.com", user.getEmail());
    }

    @Test
    void testUserNoArgsConstructor_ShouldWork() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void testUserAllArgsConstructor_ShouldWork() {
        User user = new User(2L, "李四", 30, "lisi@example.com");
        assertNotNull(user);
        assertEquals(2L, user.getId());
        assertEquals("李四", user.getName());
    }

    @Test
    void testUserSetters_ShouldWork() {
        User user = new User();
        user.setId(2L);
        user.setName("李四");
        user.setAge(30);
        user.setEmail("lisi@example.com");

        assertEquals(2L, user.getId());
        assertEquals("李四", user.getName());
        assertEquals(30, user.getAge());
        assertEquals("lisi@example.com", user.getEmail());
    }
}