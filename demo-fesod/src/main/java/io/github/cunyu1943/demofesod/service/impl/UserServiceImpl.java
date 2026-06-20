package io.github.cunyu1943.demofesod.service.impl;

import io.github.cunyu1943.demofesod.dto.CreateUserRequest;
import io.github.cunyu1943.demofesod.dto.Result;
import io.github.cunyu1943.demofesod.dto.UserDTO;
import io.github.cunyu1943.demofesod.entity.User;
import io.github.cunyu1943.demofesod.repository.UserRepository;
import io.github.cunyu1943.demofesod.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fesod.sheet.FesodSheet;
import org.apache.fesod.sheet.context.AnalysisContext;
import org.apache.fesod.sheet.event.AnalysisEventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 用户信息
     */
    @Override
    @Transactional
    public Result<UserDTO> createUser(CreateUserRequest request) {
        log.info("创建用户: username={}", request.getUsername());

        // 检查用户名是否存在
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("用户名已存在: {}", request.getUsername());
            return Result.fail(400, "用户名已存在");
        }

        // 检查邮箱是否存在
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

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Override
    public Result<List<UserDTO>> getAllUsers() {
        log.info("查询所有用户");
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTO::fromEntity)
                .toList();
        log.info("查询到 {} 个用户", userDTOs.size());
        return Result.success(userDTOs);
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public Result<UserDTO> getUserById(Long id) {
        log.info("查询用户: id={}", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            log.warn("用户不存在: id={}", id);
            return Result.fail(404, "用户不存在");
        }
        return Result.success(UserDTO.fromEntity(userOptional.get()));
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @Override
    @Transactional
    public Result<Void> deleteUser(Long id) {
        log.info("删除用户: id={}", id);
        if (!userRepository.existsById(id)) {
            log.warn("用户不存在: id={}", id);
            return Result.fail(404, "用户不存在");
        }
        userRepository.deleteById(id);
        log.info("用户删除成功: id={}", id);
        return Result.success();
    }

    /**
     * 导出所有用户到Excel
     *
     * @param filePath 导出文件路径
     * @return 操作结果
     */
    @Override
    public Result<String> exportUsersToExcel(String filePath) {
        log.info("导出用户到Excel: filePath={}", filePath);
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOs = users.stream()
                    .map(UserDTO::fromEntity)
                    .toList();
            FesodSheet.write(filePath, UserDTO.class)
                    .sheet("用户列表")
                    .doWrite(userDTOs);
            log.info("导出成功: {} 条记录", userDTOs.size());
            return Result.success(filePath);
        } catch (Exception e) {
            log.error("导出失败", e);
            return Result.fail("导出失败: " + e.getMessage());
        }
    }

    /**
     * 从Excel导入用户
     *
     * @param filePath 导入文件路径
     * @return 导入结果
     */
    @Override
    @Transactional
    public Result<Integer> importUsersFromExcel(String filePath) {
        log.info("从Excel导入用户: filePath={}", filePath);
        try {
            List<UserDTO> importedUsers = new ArrayList<>();

            // 创建自定义监听器
            AnalysisEventListener<UserDTO> listener = new AnalysisEventListener<UserDTO>() {
                @Override
                public void invoke(UserDTO data, AnalysisContext context) {
                    importedUsers.add(data);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    log.info("Excel读取完成，共读取 {} 条记录", importedUsers.size());
                }
            };

            FesodSheet.read(filePath, UserDTO.class, listener)
                    .sheet()
                    .doRead();

            int importCount = 0;
            for (UserDTO userDTO : importedUsers) {
                if (userRepository.existsByUsername(userDTO.getUsername())) {
                    log.warn("跳过已存在的用户: {}", userDTO.getUsername());
                    continue;
                }

                User user = new User();
                user.setUsername(userDTO.getUsername());
                user.setEmail(userDTO.getEmail());
                user.setPassword(passwordEncoder.encode("123456")); // 默认密码
                userRepository.save(user);
                importCount++;
            }

            log.info("导入成功: {} 条记录", importCount);
            return Result.success(importCount);
        } catch (Exception e) {
            log.error("导入失败", e);
            return Result.fail("导入失败: " + e.getMessage());
        }
    }
}
