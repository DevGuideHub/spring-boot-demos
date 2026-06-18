package io.github.cunyu1943.demoemail.service;

import io.github.cunyu1943.demoemail.dto.EmailRequest;
import io.github.cunyu1943.demoemail.dto.EmailResponse;
import io.github.cunyu1943.demoemail.service.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @description: EmailServiceImpl 单元测试
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        emailService.setFromEmail("noreply@example.com");
    }

    // ==================== sendSimpleMail 测试 ====================

    @Test
    void testSendSimpleMail_Success() {
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        EmailResponse response = emailService.sendSimpleMail("test@example.com", "测试主题", "测试内容");

        assertTrue(response.isSuccess());
        assertEquals("邮件发送成功", response.getMessage());
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendSimpleMail_InvalidEmail() {
        EmailResponse response = emailService.sendSimpleMail("invalid-email", "测试主题", "测试内容");

        assertFalse(response.isSuccess());
        assertEquals("无效的收件人邮箱", response.getMessage());
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendSimpleMail_NullEmail() {
        EmailResponse response = emailService.sendSimpleMail(null, "测试主题", "测试内容");

        assertFalse(response.isSuccess());
        assertEquals("无效的收件人邮箱", response.getMessage());
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendSimpleMail_EmptyEmail() {
        EmailResponse response = emailService.sendSimpleMail("", "测试主题", "测试内容");

        assertFalse(response.isSuccess());
        assertEquals("无效的收件人邮箱", response.getMessage());
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendSimpleMail_SendException() {
        doThrow(new RuntimeException("SMTP连接失败")).when(javaMailSender).send(any(SimpleMailMessage.class));

        EmailResponse response = emailService.sendSimpleMail("test@example.com", "测试主题", "测试内容");

        assertFalse(response.isSuccess());
        assertTrue(response.getMessage().contains("邮件发送失败"));
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    // ==================== sendHtmlMail 测试 ====================

    @Test
    void testSendHtmlMail_Success() throws MessagingException {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        EmailResponse response = emailService.sendHtmlMail("test@example.com", "测试主题", "<h1>HTML内容</h1>");

        assertTrue(response.isSuccess());
        assertEquals("HTML邮件发送成功", response.getMessage());
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void testSendHtmlMail_InvalidEmail() {
        EmailResponse response = emailService.sendHtmlMail("invalid-email", "测试主题", "<h1>HTML内容</h1>");

        assertFalse(response.isSuccess());
        assertEquals("无效的收件人邮箱", response.getMessage());
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void testSendHtmlMail_NullEmail() {
        EmailResponse response = emailService.sendHtmlMail(null, "测试主题", "<h1>HTML内容</h1>");

        assertFalse(response.isSuccess());
        assertEquals("无效的收件人邮箱", response.getMessage());
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void testSendHtmlMail_EmptyEmail() {
        EmailResponse response = emailService.sendHtmlMail("", "测试主题", "<h1>HTML内容</h1>");

        assertFalse(response.isSuccess());
        assertEquals("无效的收件人邮箱", response.getMessage());
        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void testSendHtmlMail_MessagingException() throws MessagingException {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new RuntimeException("邮件发送失败")).when(javaMailSender).send(any(MimeMessage.class));

        EmailResponse response = emailService.sendHtmlMail("test@example.com", "测试主题", "<h1>HTML内容</h1>");

        assertFalse(response.isSuccess());
        assertTrue(response.getMessage().contains("HTML邮件发送失败"));
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    // ==================== sendMail 测试 ====================

    @Test
    void testSendMail_NullRequest() {
        EmailResponse response = emailService.sendMail(null);

        assertFalse(response.isSuccess());
        assertEquals("请求对象不能为空", response.getMessage());
    }

    @Test
    void testSendMail_TextMail_Success() {
        EmailRequest request = EmailRequest.builder()
                .to("test@example.com")
                .subject("测试主题")
                .content("测试内容")
                .html(false)
                .build();
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        EmailResponse response = emailService.sendMail(request);

        assertTrue(response.isSuccess());
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendMail_HtmlMail_Success() throws MessagingException {
        EmailRequest request = EmailRequest.builder()
                .to("test@example.com")
                .subject("测试主题")
                .content("<h1>HTML内容</h1>")
                .html(true)
                .build();
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        EmailResponse response = emailService.sendMail(request);

        assertTrue(response.isSuccess());
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    // ==================== isValidEmail 测试 ====================

    @Test
    void testIsValidEmail_ValidEmail() {
        assertTrue(emailService.isValidEmail("test@example.com"));
        assertTrue(emailService.isValidEmail("user.name@domain.co.uk"));
        assertTrue(emailService.isValidEmail("user+tag@example.org"));
    }

    @Test
    void testIsValidEmail_InvalidEmail_NoAt() {
        assertFalse(emailService.isValidEmail("testexample.com"));
    }

    @Test
    void testIsValidEmail_InvalidEmail_NoDomain() {
        assertFalse(emailService.isValidEmail("test@"));
    }

    @Test
    void testIsValidEmail_InvalidEmail_NoUsername() {
        assertFalse(emailService.isValidEmail("@example.com"));
    }

    @Test
    void testIsValidEmail_InvalidEmail_NoTld() {
        assertFalse(emailService.isValidEmail("test@example"));
    }

    @Test
    void testIsValidEmail_InvalidEmail_SpecialChars() {
        assertFalse(emailService.isValidEmail("test@exam!ple.com"));
    }

    @Test
    void testIsValidEmail_NullEmail() {
        assertFalse(emailService.isValidEmail(null));
    }

    @Test
    void testIsValidEmail_EmptyEmail() {
        assertFalse(emailService.isValidEmail(""));
    }

    @Test
    void testIsValidEmail_SpaceEmail() {
        assertFalse(emailService.isValidEmail(" "));
    }

    // ==================== 其他测试 ====================

    @Test
    void testSetFromEmail() {
        emailService.setFromEmail("custom@example.com");
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        EmailResponse response = emailService.sendSimpleMail("test@example.com", "测试", "内容");

        assertTrue(response.isSuccess());
    }

    @Test
    void testEmailRequest_Builder() {
        EmailRequest request = EmailRequest.builder()
                .to("to@example.com")
                .subject("主题")
                .content("内容")
                .html(true)
                .build();

        assertEquals("to@example.com", request.getTo());
        assertEquals("主题", request.getSubject());
        assertEquals("内容", request.getContent());
        assertTrue(request.isHtml());
    }

    @Test
    void testEmailResponse_Builder() {
        EmailResponse response = EmailResponse.builder()
                .success(true)
                .message("成功")
                .build();

        assertTrue(response.isSuccess());
        assertEquals("成功", response.getMessage());
    }

    @Test
    void testEmailRequest_NoArgsConstructor() {
        EmailRequest request = new EmailRequest();
        assertNotNull(request);
    }

    @Test
    void testEmailResponse_NoArgsConstructor() {
        EmailResponse response = new EmailResponse();
        assertNotNull(response);
    }

    @Test
    void testEmailRequest_Setters() {
        EmailRequest request = new EmailRequest();
        request.setTo("to@example.com");
        request.setSubject("主题");
        request.setContent("内容");
        request.setHtml(false);

        assertEquals("to@example.com", request.getTo());
        assertEquals("主题", request.getSubject());
        assertEquals("内容", request.getContent());
        assertFalse(request.isHtml());
    }

    @Test
    void testEmailResponse_Setters() {
        EmailResponse response = new EmailResponse();
        response.setSuccess(true);
        response.setMessage("成功");

        assertTrue(response.isSuccess());
        assertEquals("成功", response.getMessage());
    }
}