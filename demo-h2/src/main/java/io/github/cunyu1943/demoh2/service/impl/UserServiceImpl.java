
package io.github.cunyu1943.demoh2.service.impl;

import io.github.cunyu1943.demoh2.dto.CreateUserRequest;
import io.github.cunyu1943.demoh2.dto.Result;
import io.github.cunyu1943.demoh2.dto.UpdateUserRequest;
import io.github.cunyu1943.demoh2.dto.UserDTO;
import io.github.cunyu1943.demoh2.entity.User;
import io.github.cunyu1943.demoh2.repository.UserRepository;
import io.github.cunyu1943.demoh2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
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
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Result<List<UserDTO>> listUsers() {
        log.info("查询所有用户");
        List<UserDTO> userDTOList = userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        log.info("查询到 {} 个用户", userDTOList.size());
        return Result.success(userDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Result<UserDTO> getUserById(Long id) {
        log.info("根据ID查询用户: id={}", id);
        return userRepository.findById(id)
                .map(user -> {
                    log.info("查询到用户: {}", user.getUsername());
                    return Result.success(UserDTO.fromEntity(user));
                })
                .orElseGet(() -> {
                    log.warn("未找到用户: id={}", id);
                    return Result.fail(404, "用户不存在");
                });
    }

    @Override
    @Transactional
    public Result<UserDTO> createUser(CreateUserRequest request) {
        log.info("创建用户: username={}, email={}", request.getUsername(), request.getEmail());

        // 检查用户名是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("用户名已存在: {}", request.getUsername());
            return Result.fail(400, "用户名已存在");
        }

        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("邮箱已存在: {}", request.getEmail());
            return Result.fail(400, "邮箱已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("用户创建成功: id={}", savedUser.getId());
        return Result.success(UserDTO.fromEntity(savedUser));
    }

    @Override
    @Transactional
    public Result<UserDTO> updateUser(Long id, UpdateUserRequest request) {
        log.info("更新用户: id={}", id);

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            log.warn("未找到用户: id={}", id);
            return Result.fail(404, "用户不存在");
        }

        User user = userOptional.get();

        // 更新用户名
        if (request != null && StringUtils.hasText(request.getUsername()) &&
                !request.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                log.warn("用户名已存在: {}", request.getUsername());
                return Result.fail(400, "用户名已存在");
            }
            user.setUsername(request.getUsername());
        }

        // 更新邮箱
        if (request != null && StringUtils.hasText(request.getEmail()) &&
                !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                log.warn("邮箱已存在: {}", request.getEmail());
                return Result.fail(400, "邮箱已存在");
            }
            user.setEmail(request.getEmail());
        }

        // 更新密码
        if (request != null && StringUtils.hasText(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        log.info("用户更新成功: id={}", updatedUser.getId());
        return Result.success(UserDTO.fromEntity(updatedUser));
    }

    @Override
    @Transactional
    public Result<Void> deleteUser(Long id) {
        log.info("删除用户: id={}", id);

        if (!userRepository.existsById(id)) {
            log.warn("未找到用户: id={}", id);
            return Result.fail(404, "用户不存在");
        }

        userRepository.deleteById(id);
        log.info("用户删除成功: id={}", id);
        return Result.success();
    }
}
