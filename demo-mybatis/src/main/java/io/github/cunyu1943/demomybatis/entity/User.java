package io.github.cunyu1943.demomybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 用户实体类
 * @author: cunyu1943
 * @date: 2026-06-22
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /** 用户ID，主键，自增 */
    private Long id;

    /** 用户姓名 */
    private String userName;

    /** 用户年龄 */
    private Integer age;

    /** 用户邮箱 */
    private String email;
}