package io.github.cunyu1943.demoemail.controller;

import io.github.cunyu1943.demoemail.dto.EmailRequest;
import io.github.cunyu1943.demoemail.dto.EmailResponse;
import io.github.cunyu1943.demoemail.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @description: EmailController 单元测试
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    private EmailResponse successResponse;
    private EmailResponse failResponse;

    @BeforeEach
    void setUp() {
        successResponse = EmailResponse.builder()
                .success(true)
                .message("邮件发送成功")
                .build();
        failResponse = EmailResponse.builder()
                .success(false)
                .message("无效的收件人邮箱")
                .build();
    }

    // ==================== sendSimpleMail 测试 ====================

    @Test
    void testSendSimpleMail_Success() {
        when(emailService.sendSimpleMail(anyString(), anyString(), anyString())).thenReturn(successResponse);

        ResponseEntity<EmailResponse> response = emailController.sendSimpleMail("test@example.com", "测试主题", "测试内容");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        verify(emailService, times(1)).sendSimpleMail("test@example.com", "测试主题", "测试内容");
    }

    @Test
    void testSendSimpleMail_Failure() {
        when(emailService.sendSimpleMail(anyString(), anyString(), anyString())).thenReturn(failResponse);

        ResponseEntity<EmailResponse> response = emailController.sendSimpleMail("invalid", "测试主题", "测试内容");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        verify(emailService, times(1)).sendSimpleMail("invalid", "测试主题", "测试内容");
    }

    @Test
    void testSendSimpleMail_EmptyTo() {
        when(emailService.sendSimpleMail(eq(""), anyString(), anyString())).thenReturn(failResponse);

        ResponseEntity<EmailResponse> response = emailController.sendSimpleMail("", "测试主题", "测试内容");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        verify(emailService, times(1)).sendSimpleMail("", "测试主题", "测试内容");
    }

    // ==================== sendHtmlMail 测试 ====================

    @Test
    void testSendHtmlMail_Success() {
        when(emailService.sendHtmlMail(anyString(), anyString(), anyString())).thenReturn(
                EmailResponse.builder().success(true).message("HTML邮件发送成功").build()
        );

        ResponseEntity<EmailResponse> response = emailController.sendHtmlMail("test@example.com", "测试主题", "<h1>HTML</h1>");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        verify(emailService, times(1)).sendHtmlMail("test@example.com", "测试主题", "<h1>HTML</h1>");
    }

    @Test
    void testSendHtmlMail_Failure() {
        when(emailService.sendHtmlMail(anyString(), anyString(), anyString())).thenReturn(failResponse);

        ResponseEntity<EmailResponse> response = emailController.sendHtmlMail("invalid", "测试主题", "<h1>HTML</h1>");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        verify(emailService, times(1)).sendHtmlMail("invalid", "测试主题", "<h1>HTML</h1>");
    }

    // ==================== sendMail 测试 ====================

    @Test
    void testSendMail_Success() {
        EmailRequest request = EmailRequest.builder()
                .to("test@example.com")
                .subject("测试主题")
                .content("测试内容")
                .html(false)
                .build();
        when(emailService.sendMail(any(EmailRequest.class))).thenReturn(successResponse);

        ResponseEntity<EmailResponse> response = emailController.sendMail(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        verify(emailService, times(1)).sendMail(any(EmailRequest.class));
    }

    @Test
    void testSendMail_Failure() {
        EmailRequest request = EmailRequest.builder()
                .to("invalid")
                .subject("测试主题")
                .content("测试内容")
                .html(false)
                .build();
        when(emailService.sendMail(any(EmailRequest.class))).thenReturn(failResponse);

        ResponseEntity<EmailResponse> response = emailController.sendMail(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        verify(emailService, times(1)).sendMail(any(EmailRequest.class));
    }

    @Test
    void testSendMail_HtmlMail() {
        EmailRequest request = EmailRequest.builder()
                .to("test@example.com")
                .subject("测试主题")
                .content("<h1>HTML</h1>")
                .html(true)
                .build();
        when(emailService.sendMail(any(EmailRequest.class))).thenReturn(
                EmailResponse.builder().success(true).message("HTML邮件发送成功").build()
        );

        ResponseEntity<EmailResponse> response = emailController.sendMail(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        verify(emailService, times(1)).sendMail(any(EmailRequest.class));
    }

    // ==================== validateEmail 测试 ====================

    @Test
    void testValidateEmail_ValidEmail() {
        when(emailService.isValidEmail("test@example.com")).thenReturn(true);

        ResponseEntity<Boolean> response = emailController.validateEmail("test@example.com");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody());
        verify(emailService, times(1)).isValidEmail("test@example.com");
    }

    @Test
    void testValidateEmail_InvalidEmail() {
        when(emailService.isValidEmail("invalid")).thenReturn(false);

        ResponseEntity<Boolean> response = emailController.validateEmail("invalid");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody());
        verify(emailService, times(1)).isValidEmail("invalid");
    }

    @Test
    void testValidateEmail_EmptyEmail() {
        when(emailService.isValidEmail("")).thenReturn(false);

        ResponseEntity<Boolean> response = emailController.validateEmail("");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody());
        verify(emailService, times(1)).isValidEmail("");
    }

    // ==================== 其他测试 ====================

    @Test
    void testControllerInstantiation() {
        assertNotNull(emailController);
    }
}