package io.github.cunyu1943.demoemail.service;

import io.github.cunyu1943.demoemail.dto.EmailRequest;
import io.github.cunyu1943.demoemail.dto.EmailResponse;

/**
 * @description: 邮件服务接口
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

public interface EmailService {

    /**
     * 发送简单文本邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return 发送结果
     */
    EmailResponse sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content HTML内容
     * @return 发送结果
     */
    EmailResponse sendHtmlMail(String to, String subject, String content);

    /**
     * 根据请求对象发送邮件
     *
     * @param request 邮件请求
     * @return 发送结果
     */
    EmailResponse sendMail(EmailRequest request);

    /**
     * 验证邮箱格式
     *
     * @param email 邮箱地址
     * @return 是否有效
     */
    boolean isValidEmail(String email);
}