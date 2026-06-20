package io.github.cunyu1943.demomybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import io.github.cunyu1943.demomybatisplus.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 用户 Mapper 接口
 * @author: cunyu1943
 * @date: 2026-06-19
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据姓名查询用户列表
     *
     * @param name 姓名
     * @return 用户列表
     */
    @Select("SELECT * FROM user WHERE user_name = #{name}")
    List<User> selectByName(@Param("name") String name);

    /**
     * 根据姓名模糊查询用户列表
     *
     * @param name 姓名关键词
     * @return 用户列表
     */
    @Select("SELECT * FROM user WHERE user_name LIKE CONCAT('%', #{name}, '%')")
    List<User> selectByNameContaining(@Param("name") String name);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    @Select("SELECT * FROM user WHERE email = #{email}")
    User selectByEmail(@Param("email") String email);

    /**
     * 根据年龄范围查询用户列表
     *
     * @param minAge 最小年龄
     * @param maxAge 最大年龄
     * @return 用户列表
     */
    @Select("SELECT * FROM user WHERE age BETWEEN #{minAge} AND #{maxAge}")
    List<User> selectByAgeBetween(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    @Select("SELECT COUNT(*) FROM user WHERE email = #{email}")
    int countByEmail(@Param("email") String email);
}
