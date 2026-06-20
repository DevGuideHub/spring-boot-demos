
package io.github.cunyu1943.demoh2.service;

import io.github.cunyu1943.demoh2.dto.CreateUserRequest;
import io.github.cunyu1943.demoh2.dto.Result;
import io.github.cunyu1943.demoh2.dto.UpdateUserRequest;
import io.github.cunyu1943.demoh2.dto.UserDTO;

import java.util.List;

/**
 * @description: 用户服务接口
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: UserService
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
    Result<List<UserDTO>> listUsers();

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    Result<UserDTO> getUserById(Long id);

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 创建的用户信息
     */
    Result<UserDTO> createUser(CreateUserRequest request);

    /**
     * 更新用户
     *
     * @param id      用户ID
     * @param request 更新用户请求
     * @return 更新后的用户信息
     */
    Result<UserDTO> updateUser(Long id, UpdateUserRequest request);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    Result<Void> deleteUser(Long id);
}
