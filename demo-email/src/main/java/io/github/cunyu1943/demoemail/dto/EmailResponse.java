package io.github.cunyu1943.demoemail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 邮件发送响应DTO
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
public class EmailResponse {

    /** 是否成功 */
    private boolean success;

    /** 响应消息 */
    private String message;
}