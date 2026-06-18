package io.github.cunyu1943.demoemail.controller;

import io.github.cunyu1943.demoemail.dto.EmailRequest;
import io.github.cunyu1943.demoemail.dto.EmailResponse;
import io.github.cunyu1943.demoemail.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 邮件控制器
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * 发送简单文本邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    @PostMapping("/simple")
    public ResponseEntity<EmailResponse> sendSimpleMail(@RequestParam String to,
                                                         @RequestParam String subject,
                                                         @RequestParam String content) {
        logger.info("发送简单邮件：to={}, subject={}", to, subject);
        EmailResponse response = emailService.sendSimpleMail(to, subject, content);
        return ResponseEntity.ok(response);
    }

    /**
     * 发送HTML邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content HTML内容
     */
    @PostMapping("/html")
    public ResponseEntity<EmailResponse> sendHtmlMail(@RequestParam String to,
                                                      @RequestParam String subject,
                                                      @RequestParam String content) {
        logger.info("发送HTML邮件：to={}, subject={}", to, subject);
        EmailResponse response = emailService.sendHtmlMail(to, subject, content);
        return ResponseEntity.ok(response);
    }

    /**
     * 发送邮件（根据请求类型自动选择简单文本或HTML）
     *
     * @param request 邮件请求
     */
    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendMail(@RequestBody EmailRequest request) {
        logger.info("发送邮件：to={}, subject={}, html={}", request.getTo(), request.getSubject(), request.isHtml());
        EmailResponse response = emailService.sendMail(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 验证邮箱格式
     *
     * @param email 邮箱地址
     */
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateEmail(@RequestParam String email) {
        logger.info("验证邮箱：email={}", email);
        boolean valid = emailService.isValidEmail(email);
        return ResponseEntity.ok(valid);
    }
}