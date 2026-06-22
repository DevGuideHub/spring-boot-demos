package io.github.cunyu1943.demomybatisflex.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 用户实体类
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: User
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user")
public class User {

    /** 用户ID，主键，自增 */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 用户姓名 */
    @Column("name")
    private String name;

    /** 用户年龄 */
    @Column("age")
    private Integer age;

    /** 用户邮箱 */
    @Column("email")
    private String email;

    /** 创建时间 */
    @Column(value = "created_at", onInsertValue = "CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @Column(value = "updated_at", onInsertValue = "CURRENT_TIMESTAMP", onUpdateValue = "CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
