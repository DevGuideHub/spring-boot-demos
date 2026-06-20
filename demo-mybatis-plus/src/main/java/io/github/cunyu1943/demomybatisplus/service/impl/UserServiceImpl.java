package io.github.cunyu1943.demomybatisplus.service.impl;

import io.github.cunyu1943.demomybatisplus.entity.User;
import io.github.cunyu1943.demomybatisplus.mapper.UserMapper;
import io.github.cunyu1943.demomybatisplus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @description: 用户服务实现类
 * @author: cunyu1943
 * @date: 2026-06-19
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("用户对象不能为空");
        }
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("用户姓名不能为空");
        }
        
        if (user.getId() == null) {
            logger.info("新增用户：userName={}", user.getUserName());
            userMapper.insert(user);
        } else {
            logger.info("更新用户：id={}, userName={}", user.getId(), user.getUserName());
            userMapper.updateById(user);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("用户ID必须大于0");
        }
        logger.info("根据ID查询用户：id={}", id);
        return Optional.ofNullable(userMapper.selectById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        logger.info("查询所有用户");
        return userMapper.selectList(null);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("用户ID必须大于0");
        }
        logger.info("删除用户：id={}", id);
        userMapper.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不能为空");
        }
        logger.info("根据姓名查询用户：name={}", name);
        return userMapper.selectByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByNameContaining(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("姓名关键词不能为空");
        }
        logger.info("模糊查询用户：name={}", name);
        return userMapper.selectByNameContaining(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("邮箱不能为空");
        }
        logger.info("根据邮箱查询用户：email={}", email);
        return Optional.ofNullable(userMapper.selectByEmail(email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByAgeBetween(Integer minAge, Integer maxAge) {
        if (minAge == null || maxAge == null) {
            throw new IllegalArgumentException("年龄范围不能为空");
        }
        if (minAge > maxAge) {
            throw new IllegalArgumentException("最小年龄不能大于最大年龄");
        }
        logger.info("根据年龄范围查询用户：minAge={}, maxAge={}", minAge, maxAge);
        return userMapper.selectByAgeBetween(minAge, maxAge);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("邮箱不能为空");
        }
        logger.info("检查邮箱是否存在：email={}", email);
        return userMapper.countByEmail(email) > 0;
    }

    @Override
    public User update(Long id, User user) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("用户ID必须大于0");
        }
        if (user == null) {
            throw new IllegalArgumentException("用户对象不能为空");
        }

        User existingUser = userMapper.selectById(id);
        if (existingUser == null) {
            throw new RuntimeException("用户不存在：id=" + id);
        }

        if (user.getUserName() != null && !user.getUserName().trim().isEmpty()) {
            existingUser.setUserName(user.getUserName());
        }
        if (user.getAge() != null) {
            existingUser.setAge(user.getAge());
        }
        if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }

        logger.info("更新用户信息：id={}", id);
        userMapper.updateById(existingUser);
        return existingUser;
    }
}
