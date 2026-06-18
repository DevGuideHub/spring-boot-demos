package io.github.cunyu1943.demoemail.service.impl;

import io.github.cunyu1943.demoemail.dto.EmailRequest;
import io.github.cunyu1943.demoemail.dto.EmailResponse;
import io.github.cunyu1943.demoemail.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * @description: 邮件服务实现类
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    /**
     * 邮箱格式正则表达式
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final JavaMailSender javaMailSender;

    /**
     * 发件人邮箱（从配置注入）
     */
    private String fromEmail = "devguidehub@qq.com";

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 设置发件人邮箱
     *
     * @param fromEmail 发件人邮箱
     */
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @Override
    public EmailResponse sendSimpleMail(String to, String subject, String content) {
        if (!isValidEmail(to)) {
            logger.warn("发送邮件失败：无效的收件人邮箱 {}", to);
            return EmailResponse.builder()
                    .success(false)
                    .message("无效的收件人邮箱")
                    .build();
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);

            logger.info("发送简单邮件成功：to={}, subject={}", to, subject);
            return EmailResponse.builder()
                    .success(true)
                    .message("邮件发送成功")
                    .build();
        } catch (Exception e) {
            logger.error("发送简单邮件失败：to={}, error={}", to, e.getMessage());
            return EmailResponse.builder()
                    .success(false)
                    .message("邮件发送失败：" + e.getMessage())
                    .build();
        }
    }

    @Override
    public EmailResponse sendHtmlMail(String to, String subject, String content) {
        if (!isValidEmail(to)) {
            logger.warn("发送HTML邮件失败：无效的收件人邮箱 {}", to);
            return EmailResponse.builder()
                    .success(false)
                    .message("无效的收件人邮箱")
                    .build();
        }

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);

            logger.info("发送HTML邮件成功：to={}, subject={}", to, subject);
            return EmailResponse.builder()
                    .success(true)
                    .message("HTML邮件发送成功")
                    .build();
        } catch (Exception e) {
            logger.error("发送HTML邮件失败：to={}, error={}", to, e.getMessage());
            return EmailResponse.builder()
                    .success(false)
                    .message("HTML邮件发送失败：" + e.getMessage())
                    .build();
        }
    }

    @Override
    public EmailResponse sendMail(EmailRequest request) {
        if (request == null) {
            logger.warn("发送邮件失败：请求对象为空");
            return EmailResponse.builder()
                    .success(false)
                    .message("请求对象不能为空")
                    .build();
        }

        if (request.isHtml()) {
            return sendHtmlMail(request.getTo(), request.getSubject(), request.getContent());
        } else {
            return sendSimpleMail(request.getTo(), request.getSubject(), request.getContent());
        }
    }

    @Override
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}