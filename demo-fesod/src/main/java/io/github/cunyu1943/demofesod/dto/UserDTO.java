package io.github.cunyu1943.demofesod.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.fesod.sheet.annotation.ExcelProperty;

import java.time.LocalDateTime;

/**
 * @description: 用户数据传输对象
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: UserDTO
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /**
     * 用户ID
     */
    @ExcelProperty("用户ID")
    private Long id;

    /**
     * 用户名
     */
    @ExcelProperty("用户名")
    private String username;

    /**
     * 用户邮箱
     */
    @ExcelProperty("邮箱")
    private String email;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    private LocalDateTime updatedAt;

    /**
     * 将User实体转换为DTO
     *
     * @param user 用户实体
     * @return 用户DTO
     */
    public static UserDTO fromEntity(io.github.cunyu1943.demofesod.entity.User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
