package io.github.cunyu1943.demojpa.repository;

import io.github.cunyu1943.demojpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @description: 用户仓储接口
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据姓名查询用户
     *
     * @param name 姓名
     * @return 用户列表
     */
    List<User> findByName(String name);

    /**
     * 根据姓名模糊查询用户
     *
     * @param name 姓名关键词
     * @return 用户列表
     */
    List<User> findByNameContaining(String name);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户（可能为空）
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
     * 检查邮箱是否已存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 根据姓名删除用户
     *
     * @param name 姓名
     * @return 删除数量
     */
    int deleteByName(String name);
}