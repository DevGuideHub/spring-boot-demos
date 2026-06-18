package io.github.cunyu1943.demojpa.service.impl;

import io.github.cunyu1943.demojpa.entity.User;
import io.github.cunyu1943.demojpa.repository.UserRepository;
import io.github.cunyu1943.demojpa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @description: 用户服务实现类
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user == null) {
            logger.warn("保存用户失败：用户对象为空");
            throw new IllegalArgumentException("用户对象不能为空");
        }
        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            logger.warn("保存用户失败：邮箱已存在");
            throw new IllegalArgumentException("邮箱已存在");
        }
        logger.info("保存用户：name={}, email={}", user.getName(), user.getEmail());
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("查询用户失败：无效的用户ID");
            return Optional.empty();
        }
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("删除用户失败：无效的用户ID");
            return;
        }
        if (!userRepository.existsById(id)) {
            logger.warn("删除用户失败：用户不存在");
            return;
        }
        userRepository.deleteById(id);
        logger.info("删除用户成功：id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByName(String name) {
        if (name == null || name.isEmpty()) {
            logger.warn("查询用户失败：姓名为空");
            return List.of();
        }
        return userRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByNameContaining(String name) {
        if (name == null || name.isEmpty()) {
            logger.warn("模糊查询用户失败：姓名关键词为空");
            return List.of();
        }
        return userRepository.findByNameContaining(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.warn("查询用户失败：邮箱为空");
            return Optional.empty();
        }
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByAgeBetween(Integer minAge, Integer maxAge) {
        if (minAge == null || maxAge == null) {
            logger.warn("查询用户失败：年龄范围参数为空");
            return List.of();
        }
        if (minAge > maxAge) {
            logger.warn("查询用户失败：最小年龄大于最大年龄");
            return List.of();
        }
        return userRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User update(Long id, User user) {
        if (id == null || id <= 0) {
            logger.warn("更新用户失败：无效的用户ID");
            throw new IllegalArgumentException("用户ID无效");
        }
        if (user == null) {
            logger.warn("更新用户失败：用户对象为空");
            throw new IllegalArgumentException("用户对象不能为空");
        }

        return userRepository.findById(id)
                .map(existingUser -> {
                    if (user.getName() != null) {
                        existingUser.setName(user.getName());
                    }
                    if (user.getAge() != null) {
                        existingUser.setAge(user.getAge());
                    }
                    if (user.getEmail() != null) {
                        existingUser.setEmail(user.getEmail());
                    }
                    logger.info("更新用户成功：id={}, name={}", id, existingUser.getName());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }
}