package io.github.cunyu1943.demomybatisplus.controller;

import io.github.cunyu1943.demomybatisplus.config.ResourceNotFoundException;
import io.github.cunyu1943.demomybatisplus.entity.User;
import io.github.cunyu1943.demomybatisplus.service.UserService;
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
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
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
                .andExpect(jsonPath("$.data[0].userName").value("测试用户"));

        verify(userService, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.data.userName").value("测试用户"));

        verify(userService, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.respCode").value(404));
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
                .andExpect(jsonPath("$.respMsg").value("创建成功"));

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_Error() throws Exception {
        when(userService.save(any(User.class))).thenThrow(new IllegalArgumentException("用户姓名不能为空"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(User.builder().build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.respCode").value(400));
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        when(userService.update(eq(1L), any(User.class))).thenReturn(testUser);

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.respMsg").value("更新成功"));

        verify(userService, times(1)).update(eq(1L), any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        when(userService.update(eq(999L), any(User.class))).thenThrow(new ResourceNotFoundException("用户", 999L));

        mockMvc.perform(put("/users/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.respCode").value(404));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        doNothing().when(userService).deleteById(1L);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200))
                .andExpect(jsonPath("$.respMsg").value("删除成功"));

        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("用户", 999L)).when(userService).deleteById(999L);

        mockMvc.perform(delete("/users/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.respCode").value(404));
    }

    @Test
    void testSearchByEmail_Success() throws Exception {
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/users/search/email")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.respCode").value(200));

        verify(userService, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testSearchByEmail_NotFound() throws Exception {
        when(userService.findByEmail("notexist@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/search/email")
                        .param("email", "notexist@example.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.respCode").value(404));
    }
}