package io.github.cunyu1943.demojdbctemplate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 用户实体类
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /** 用户ID */
    private Long id;

    /** 用户名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 邮箱 */
    private String email;
}