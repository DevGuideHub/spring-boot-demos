package io.github.cunyu1943.demosbeetl.service;

import io.github.cunyu1943.demosbeetl.dto.Result;
import io.github.cunyu1943.demosbeetl.dto.UserDTO;

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
     * 获取所有用户
     *
     * @return 用户列表
     */
    Result<List<UserDTO>> listUsers();

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    Result<UserDTO> getUserById(Long id);

    /**
     * 获取欢迎消息
     *
     * @param name 名称
     * @return 欢迎消息
     */
    Result<String> getWelcomeMessage(String name);

}