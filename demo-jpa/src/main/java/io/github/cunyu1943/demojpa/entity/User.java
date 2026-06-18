package io.github.cunyu1943.demojpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    /** 用户ID，主键，自增 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户姓名 */
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /** 用户年龄 */
    @Column(name = "age")
    private Integer age;

    /** 用户邮箱 */
    @Column(name = "email", length = 100)
    private String email;
}