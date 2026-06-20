package io.github.cunyu1943.demofesod.service;

import io.github.cunyu1943.demofesod.dto.CreateUserRequest;
import io.github.cunyu1943.demofesod.dto.Result;
import io.github.cunyu1943.demofesod.dto.UserDTO;

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
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 用户信息
     */
    Result<UserDTO> createUser(CreateUserRequest request);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    Result<List<UserDTO>> getAllUsers();

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    Result<UserDTO> getUserById(Long id);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    Result<Void> deleteUser(Long id);

    /**
     * 导出所有用户到Excel
     *
     * @param filePath 导出文件路径
     * @return 操作结果
     */
    Result<String> exportUsersToExcel(String filePath);

    /**
     * 从Excel导入用户
     *
     * @param filePath 导入文件路径
     * @return 导入结果
     */
    Result<Integer> importUsersFromExcel(String filePath);
}
