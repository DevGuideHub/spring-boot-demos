package io.github.cunyu1943.demojdbctemplate.service;

import io.github.cunyu1943.demojdbctemplate.entity.User;

import java.util.List;

/**
 * @description: 用户服务接口
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

public interface UserService {

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User findById(Long id);

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 影响行数
     */
    int update(User user);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 统计用户总数
     *
     * @return 用户数量
     */
    int count();
}