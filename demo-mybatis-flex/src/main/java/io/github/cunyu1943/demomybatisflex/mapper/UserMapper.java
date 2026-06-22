package io.github.cunyu1943.demomybatisflex.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import io.github.cunyu1943.demomybatisflex.entity.User;

/**
 * @description: 用户 Mapper 接口
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: UserMapper
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
