package io.github.cunyu1943.demoemail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 邮件发送请求DTO
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
public class EmailRequest {

    /** 收件人邮箱 */
    private String to;

    /** 邮件主题 */
    private String subject;

    /** 邮件内容 */
    private String content;

    /** 是否为HTML邮件 */
    private boolean html;
}