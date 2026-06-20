package io.github.cunyu1943.demoenjoy.service.impl;

import io.github.cunyu1943.demoenjoy.dto.CreateUserRequest;
import io.github.cunyu1943.demoenjoy.dto.Result;
import io.github.cunyu1943.demoenjoy.dto.UserDTO;
import io.github.cunyu1943.demoenjoy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
     * 用户数据存储（内存模拟）
     */
    private final ConcurrentHashMap<Long, UserDTO> userStorage = new ConcurrentHashMap<>();

    /**
     * ID 生成器
     */
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Result<List<UserDTO>> listUsers() {
        log.info("查询所有用户");
        try {
            List<UserDTO> users = new ArrayList<>(userStorage.values());
            log.debug("查询到 {} 个用户", users.size());
            return Result.success(users);
        } catch (Exception e) {
            log.error("查询用户列表失败", e);
            return Result.fail("查询用户列表失败：" + e.getMessage());
        }
    }

    @Override
    public Result<UserDTO> getUserById(Long id) {
        log.info("根据ID查询用户，ID: {}", id);

        if (id == null || id <= 0) {
            log.warn("用户ID无效: {}", id);
            return Result.fail("用户ID无效");
        }

        try {
            UserDTO user = userStorage.get(id);
            if (user == null) {
                log.warn("用户不存在，ID: {}", id);
                return Result.fail("用户不存在");
            }
            log.debug("找到用户: {}", user);
            return Result.success(user);
        } catch (Exception e) {
            log.error("查询用户失败，ID: {}", id, e);
            return Result.fail("查询用户失败：" + e.getMessage());
        }
    }

    @Override
    public Result<UserDTO> createUser(CreateUserRequest request) {
        log.info("创建用户，请求: {}", request);

        if (request == null || request.getUsername() == null || request.getUsername().isEmpty()) {
            log.warn("创建用户失败：用户名不能为空");
            return Result.fail("用户名不能为空");
        }

        try {
            Long id = idGenerator.getAndIncrement();

            UserDTO user = UserDTO.builder()
                    .id(id)
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .build();

            userStorage.put(id, user);
            log.info("用户创建成功，ID: {}", id);
            return Result.success(user);
        } catch (Exception e) {
            log.error("创建用户失败，请求: {}", request, e);
            return Result.fail("创建用户失败：" + e.getMessage());
        }
    }

    @Override
    public Result<Void> deleteUserById(Long id) {
        log.info("删除用户，ID: {}", id);

        if (id == null || id <= 0) {
            log.warn("删除用户失败：用户ID无效");
            return Result.fail("用户ID无效");
        }

        try {
            UserDTO removed = userStorage.remove(id);

            if (removed != null) {
                log.info("用户删除成功，ID: {}", id);
                return Result.success();
            } else {
                log.warn("删除用户失败：用户不存在，ID: {}", id);
                return Result.fail("用户不存在");
            }
        } catch (Exception e) {
            log.error("删除用户失败，ID: {}", id, e);
            return Result.fail("删除用户失败：" + e.getMessage());
        }
    }

}
