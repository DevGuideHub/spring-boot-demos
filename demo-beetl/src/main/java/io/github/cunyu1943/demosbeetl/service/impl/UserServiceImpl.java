package io.github.cunyu1943.demosbeetl.service.impl;

import io.github.cunyu1943.demosbeetl.dto.Result;
import io.github.cunyu1943.demosbeetl.dto.UserDTO;
import io.github.cunyu1943.demosbeetl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description: 用户服务实现类
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: UserServiceImpl
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * 用户数据列表（模拟数据）
     */
    private final List<UserDTO> userList = new ArrayList<>();

    /**
     * 构造函数，初始化模拟数据
     */
    public UserServiceImpl() {
        userList.add(UserDTO.builder()
                .id(1L)
                .username("zhangsan")
                .email("zhangsan@example.com")
                .createTime(LocalDateTime.now())
                .build());
        userList.add(UserDTO.builder()
                .id(2L)
                .username("lisi")
                .email("lisi@example.com")
                .createTime(LocalDateTime.now())
                .build());
        userList.add(UserDTO.builder()
                .id(3L)
                .username("wangwu")
                .email("wangwu@example.com")
                .createTime(LocalDateTime.now())
                .build());
    }

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    @Override
    public Result<List<UserDTO>> listUsers() {
        log.info("查询所有用户");
        log.debug("查询到 {} 个用户", userList.size());
        return Result.success(userList);
    }

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public Result<UserDTO> getUserById(Long id) {
        log.info("根据ID查询用户，ID: {}", id);
        if (Objects.isNull(id) || id <= 0) {
            log.warn("用户ID无效: {}", id);
            return Result.fail("用户ID无效");
        }
        UserDTO user = userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(user)) {
            log.warn("用户不存在，ID: {}", id);
            return Result.fail("用户不存在");
        }
        log.debug("找到用户: {}", user);
        return Result.success(user);
    }

    /**
     * 获取欢迎消息
     *
     * @param name 名称
     * @return 欢迎消息
     */
    @Override
    public Result<String> getWelcomeMessage(String name) {
        log.info("获取欢迎消息，名称: {}", name);
        if (Objects.isNull(name) || name.isEmpty()) {
            log.warn("名称为空");
            return Result.fail("名称不能为空");
        }
        String message = "欢迎 " + name + " 来到 Spring Boot 世界！";
        log.debug("返回消息: {}", message);
        return Result.success(message);
    }

}