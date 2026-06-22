package io.github.cunyu1943.demomybatisflex.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import io.github.cunyu1943.demomybatisflex.entity.User;
import io.github.cunyu1943.demomybatisflex.mapper.UserMapper;
import io.github.cunyu1943.demomybatisflex.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @description: 用户服务实现类
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: UserServiceImpl
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user == null) {
            logger.warn("保存用户失败：用户对象为空");
            throw new IllegalArgumentException("用户对象不能为空");
        }
        logger.debug("保存用户：id={}, name={}, age={}, email={}", user.getId(), user.getName(), user.getAge(), user.getEmail());

        if (user.getEmail() != null && existsByEmail(user.getEmail())) {
            logger.warn("保存用户失败：邮箱已存在");
            throw new IllegalArgumentException("邮箱已存在");
        }
        if (user.getId() == null) {
            userMapper.insert(user);
            logger.info("创建用户成功：id={}, name={}", user.getId(), user.getName());
        } else {
            logger.debug("用户已有ID，执行更新操作：id={}", user.getId());
            userMapper.update(user);
            logger.info("更新用户成功：id={}, name={}", user.getId(), user.getName());
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("查询用户失败：无效的用户ID");
            return Optional.empty();
        }
        return Optional.ofNullable(userMapper.selectOneById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userMapper.selectListByQuery(QueryWrapper.create());
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("删除用户失败：无效的用户ID");
            return false;
        }
        int affected = userMapper.deleteById(id);
        if (affected > 0) {
            logger.info("删除用户成功：id={}", id);
            return true;
        }
        logger.warn("删除用户失败：用户不存在");
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByName(String name) {
        if (name == null || name.isEmpty()) {
            logger.warn("查询用户失败：姓名为空");
            return List.of();
        }
        QueryWrapper queryWrapper = QueryWrapper.create().eq("name", name).orderBy("id", true);
        return userMapper.selectListByQuery(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.warn("查询用户失败：邮箱为空");
            return Optional.empty();
        }
        QueryWrapper queryWrapper = QueryWrapper.create().eq("email", email);
        return Optional.ofNullable(userMapper.selectOneByQuery(queryWrapper));
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
        QueryWrapper queryWrapper = QueryWrapper.create().between("age", minAge, maxAge).orderBy("age", true);
        return userMapper.selectListByQuery(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        QueryWrapper queryWrapper = QueryWrapper.create().eq("email", email);
        return userMapper.selectCountByQuery(queryWrapper) > 0;
    }
}
