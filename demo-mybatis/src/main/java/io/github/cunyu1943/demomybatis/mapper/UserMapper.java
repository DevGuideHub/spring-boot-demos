package io.github.cunyu1943.demomybatis.mapper;

import io.github.cunyu1943.demomybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: 用户 Mapper 接口
 * @author: cunyu1943
 * @date: 2026-06-22
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Mapper
public interface UserMapper {

    /**
     * 插入用户
     *
     * @param user 用户对象
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    User selectById(Long id);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> selectAll();

    /**
     * 根据ID更新用户
     *
     * @param user 用户对象
     * @return 影响行数
     */
    int updateById(User user);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据姓名查询用户列表
     *
     * @param name 姓名
     * @return 用户列表
     */
    List<User> selectByName(String name);

    /**
     * 根据姓名模糊查询用户列表
     *
     * @param name 姓名关键词
     * @return 用户列表
     */
    List<User> selectByNameContaining(String name);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    User selectByEmail(String email);

    /**
     * 根据年龄范围查询用户列表
     *
     * @param minAge 最小年龄
     * @param maxAge 最大年龄
     * @return 用户列表
     */
    List<User> selectByAgeBetween(Integer minAge, Integer maxAge);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 数量
     */
    int countByEmail(String email);
}