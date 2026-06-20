
package io.github.cunyu1943.demoh2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * @description: 更新用户请求 DTO
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: UpdateUserRequest
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
public class UpdateUserRequest {

    /**
     * 用户名
     */
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50之间")
    private String username;

    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 用户密码
     */
    @Size(min = 6, max = 100, message = "密码长度必须在6-100之间")
    private String password;

    /**
     * 默认构造函数
     */
    public UpdateUserRequest() {
    }

    /**
     * 全参构造函数
     */
    public UpdateUserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
