package io.github.cunyu1943.demojdbctemplate.service.impl;

import io.github.cunyu1943.demojdbctemplate.entity.User;
import io.github.cunyu1943.demojdbctemplate.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private final JdbcTemplate jdbcTemplate;

    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** 用户行映射器 */
    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM user", USER_ROW_MAPPER);
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?", USER_ROW_MAPPER, id);
    }

    @Override
    public int insert(User user) {
        return jdbcTemplate.update("INSERT INTO user (name, age, email) VALUES (?, ?, ?)",
                user.getName(), user.getAge(), user.getEmail());
    }

    @Override
    public int update(User user) {
        return jdbcTemplate.update("UPDATE user SET name = ?, age = ?, email = ? WHERE id = ?",
                user.getName(), user.getAge(), user.getEmail(), user.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM user WHERE id = ?", id);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
    }
}