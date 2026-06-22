package io.github.cunyu1943.demomybatis.controller;

import io.github.cunyu1943.demomybatis.config.GlobalExceptionHandler;
import io.github.cunyu1943.demomybatis.config.ResourceNotFoundException;
import io.github.cunyu1943.demomybatis.entity.User;
import io.github.cunyu1943.demomybatis.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @description: 用户控制器测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-22
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User testUser;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        testUser = User.builder()
                .id(1L)
                .userName("测试用户")
                .age(25)
                .email("test@example.com")
                .build();
    }

    @Test
    void testGetAllUsers_Success() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(testUser));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.respMsg").value("查询成功"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].userName").value("测试用户"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_Empty() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());

        verify(userService, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.data.userName").value("测试用户"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.respCode").value(404))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void testCreateUser_Success() throws Exception {
        when(userService.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(201))
                .andExpect(jsonPath("$.respMsg").value("创建成功"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_Error() throws Exception {
        when(userService.save(any(User.class))).thenThrow(new IllegalArgumentException("用户姓名不能为空"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(User.builder().build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.respCode").value(400))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        when(userService.update(eq(1L), any(User.class))).thenReturn(testUser);

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.respMsg").value("更新成功"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).update(eq(1L), any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        when(userService.update(eq(999L), any(User.class))).thenThrow(new ResourceNotFoundException("用户", 999L));

        mockMvc.perform(put("/users/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.respCode").value(404))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        doNothing().when(userService).deleteById(1L);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.respMsg").value("删除成功"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("用户", 999L)).when(userService).deleteById(999L);

        mockMvc.perform(delete("/users/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.respCode").value(404))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void testSearchByName_Success() throws Exception {
        when(userService.findByName("测试用户")).thenReturn(Arrays.asList(testUser));

        mockMvc.perform(get("/users/search/name")
                        .param("name", "测试用户"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).findByName("测试用户");
    }

    @Test
    void testSearchByNameContaining_Success() throws Exception {
        when(userService.findByNameContaining("测试")).thenReturn(Arrays.asList(testUser));

        mockMvc.perform(get("/users/search/name-contain")
                        .param("name", "测试"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).findByNameContaining("测试");
    }

    @Test
    void testSearchByEmail_Success() throws Exception {
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/users/search/email")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testSearchByEmail_NotFound() throws Exception {
        when(userService.findByEmail("notexist@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/search/email")
                        .param("email", "notexist@example.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.respCode").value(404))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void testSearchByAgeRange_Success() throws Exception {
        when(userService.findByAgeBetween(20, 30)).thenReturn(Arrays.asList(testUser));

        mockMvc.perform(get("/users/search/age-range")
                        .param("minAge", "20")
                        .param("maxAge", "30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).findByAgeBetween(20, 30);
    }

    @Test
    void testCheckEmailExists_Exists() throws Exception {
        when(userService.existsByEmail("test@example.com")).thenReturn(true);

        mockMvc.perform(get("/users/check-email")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void testCheckEmailExists_NotExists() throws Exception {
        when(userService.existsByEmail("notexist@example.com")).thenReturn(false);

        mockMvc.perform(get("/users/check-email")
                        .param("email", "notexist@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.data").value(false))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService, times(1)).existsByEmail("notexist@example.com");
    }
}