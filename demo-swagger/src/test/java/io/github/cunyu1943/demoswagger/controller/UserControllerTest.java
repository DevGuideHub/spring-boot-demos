package io.github.cunyu1943.demoswagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cunyu1943.demoswagger.dto.CreateUserRequest;
import io.github.cunyu1943.demoswagger.dto.Result;
import io.github.cunyu1943.demoswagger.dto.UserDTO;
import io.github.cunyu1943.demoswagger.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @description: 用户控制器测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("UserController 用户控制器测试")
class UserControllerTest {

    /**
     * MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * 用户服务Mock
     */
    @MockBean
    private UserService userService;

    /**
     * ObjectMapper
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("listUsers - 返回用户列表")
    void testListUsers() throws Exception {
        UserDTO user1 = UserDTO.builder()
                .id(1L)
                .username("zhangsan")
                .email("zhangsan@example.com")
                .createTime(LocalDateTime.now())
                .build();
        UserDTO user2 = UserDTO.builder()
                .id(2L)
                .username("lisi")
                .email("lisi@example.com")
                .createTime(LocalDateTime.now())
                .build();
        Result successResult = Result.success(Arrays.asList(user1, user2));
        when(userService.listUsers()).thenReturn(successResult);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.respMsg").value("操作成功"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));

        verify(userService, times(1)).listUsers();
    }

    @Test
    @DisplayName("listUsers - 空列表")
    void testListUsersEmpty() throws Exception {
        Result successResult = Result.success(Collections.emptyList());
        when(userService.listUsers()).thenReturn(successResult);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));

        verify(userService, times(1)).listUsers();
    }

    @Test
    @DisplayName("getUserById - 成功获取用户")
    void testGetUserById() throws Exception {
        UserDTO user = UserDTO.builder()
                .id(1L)
                .username("zhangsan")
                .email("zhangsan@example.com")
                .createTime(LocalDateTime.now())
                .build();
        Result successResult = Result.success(user);
        when(userService.getUserById(1L)).thenReturn(successResult);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("zhangsan"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    @DisplayName("getUserById - 用户不存在")
    void testGetUserByIdNotFound() throws Exception {
        Result failResult = Result.fail("用户不存在");
        when(userService.getUserById(999L)).thenReturn(failResult);

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(500))
                .andExpect(jsonPath("$.respMsg").value("用户不存在"));

        verify(userService, times(1)).getUserById(999L);
    }

    @Test
    @DisplayName("createUser - 创建用户成功")
    void testCreateUser() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("zhangsan")
                .email("zhangsan@example.com")
                .build();
        UserDTO user = UserDTO.builder()
                .id(1L)
                .username("zhangsan")
                .email("zhangsan@example.com")
                .createTime(LocalDateTime.now())
                .build();
        Result successResult = Result.success(user);
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(successResult);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.data.username").value("zhangsan"));

        verify(userService, times(1)).createUser(any(CreateUserRequest.class));
    }

    @Test
    @DisplayName("deleteUser - 删除用户成功")
    void testDeleteUser() throws Exception {
        Result successResult = Result.success();
        when(userService.deleteUserById(1L)).thenReturn(successResult);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.respMsg").value("操作成功"));

        verify(userService, times(1)).deleteUserById(1L);
    }

    @Test
    @DisplayName("deleteUser - 用户不存在")
    void testDeleteUserNotFound() throws Exception {
        Result failResult = Result.fail("用户不存在");
        when(userService.deleteUserById(999L)).thenReturn(failResult);

        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(500))
                .andExpect(jsonPath("$.respMsg").value("用户不存在"));

        verify(userService, times(1)).deleteUserById(999L);
    }

}