package io.github.cunyu1943.demomybatisflex.service;

import io.github.cunyu1943.demomybatisflex.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @description: 用户服务接口
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: UserService
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

public interface UserService {

    /**
     * 保存用户（新增或更新）
     *
     * @param user 用户对象
     * @return 保存后的用户对象
     */
    User save(User user);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户对象（可选）
     */
    Optional<User> findById(Long id);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 删除是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据姓名查询用户
     *
     * @param name 用户姓名
     * @return 用户列表
     */
    List<User> findByName(String name);

    /**
     * 根据邮箱查询用户
     *
     * @param email 用户邮箱
     * @return 用户对象（可选）
     */
    Optional<User> findByEmail(String email);

    /**
     * 根据年龄范围查询用户
     *
     * @param minAge 最小年龄
     * @param maxAge 最大年龄
     * @return 用户列表
     */
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);

    /**
     * 检查邮箱是否存在
     *
     * @param email 用户邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);
}
