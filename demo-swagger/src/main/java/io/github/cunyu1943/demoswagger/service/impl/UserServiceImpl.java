package io.github.cunyu1943.demoswagger.service.impl;

import io.github.cunyu1943.demoswagger.dto.CreateUserRequest;
import io.github.cunyu1943.demoswagger.dto.Result;
import io.github.cunyu1943.demoswagger.dto.UserDTO;
import io.github.cunyu1943.demoswagger.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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
     * 用户数据存储（模拟数据库）
     */
    private final ConcurrentHashMap<Long, UserDTO> userStore = new ConcurrentHashMap<>();

    /**
     * ID生成器
     */
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    @Override
    public Result<List<UserDTO>> listUsers() {
        log.info("查询所有用户");
        List<UserDTO> users = new ArrayList<>(userStore.values());
        log.debug("查询到 {} 个用户", users.size());
        return Result.success(users);
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
        UserDTO user = userStore.get(id);
        if (Objects.isNull(user)) {
            log.warn("用户不存在，ID: {}", id);
            return Result.fail("用户不存在");
        }
        log.debug("找到用户: {}", user);
        return Result.success(user);
    }

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 创建的用户信息
     */
    @Override
    public Result<UserDTO> createUser(CreateUserRequest request) {
        log.info("创建用户，请求: {}", request);
        if (Objects.isNull(request) || Objects.isNull(request.getUsername()) || request.getUsername().isEmpty()) {
            log.warn("创建用户失败：用户名不能为空");
            return Result.fail("用户名不能为空");
        }
        Long id = idGenerator.getAndIncrement();
        UserDTO user = UserDTO.builder()
                .id(id)
                .username(request.getUsername())
                .email(request.getEmail())
                .createTime(LocalDateTime.now())
                .build();
        userStore.put(id, user);
        log.info("用户创建成功，ID: {}", id);
        return Result.success(user);
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    @Override
    public Result<Void> deleteUserById(Long id) {
        log.info("删除用户，ID: {}", id);
        if (Objects.isNull(id) || id <= 0) {
            log.warn("删除用户失败：用户ID无效");
            return Result.fail("用户ID无效");
        }
        UserDTO removed = userStore.remove(id);
        if (Objects.isNull(removed)) {
            log.warn("删除用户失败：用户不存在，ID: {}", id);
            return Result.fail("用户不存在");
        }
        log.info("用户删除成功，ID: {}", id);
        return Result.success();
    }

}