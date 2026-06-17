package io.github.cunyu1943.demothymeleaf.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: ThymeleafController 单元测试（使用 JUnit 5 + Mockito）
 * @author: cunyu1943
 * @date: 2026-06-17
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@ExtendWith(MockitoExtension.class)
class ThymeleafControllerTest {

    private ThymeleafController thymeleafController;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        thymeleafController = new ThymeleafController();
    }

    @Test
    void testIndex_ShouldReturnIndexTemplate() {
        String result = thymeleafController.index(model);
        assertEquals("index", result);
    }

    @Test
    void testIndex_ShouldNotThrowException() {
        assertDoesNotThrow(() -> thymeleafController.index(model));
    }

    @Test
    void testUser_ShouldReturnUserTemplate() {
        String result = thymeleafController.user(model, "村雨");
        assertEquals("user", result);
    }

    @Test
    void testUser_WithNullName_ShouldReturnUserTemplate() {
        String result = thymeleafController.user(model, null);
        assertEquals("user", result);
    }

    @Test
    void testUser_WithEmptyName_ShouldReturnUserTemplate() {
        String result = thymeleafController.user(model, "");
        assertEquals("user", result);
    }

    @Test
    void testList_ShouldReturnListTemplate() {
        String result = thymeleafController.list(model);
        assertEquals("list", result);
    }

    @Test
    void testList_ShouldNotThrowException() {
        assertDoesNotThrow(() -> thymeleafController.list(model));
    }

    @Test
    void testConditional_WithTrueFlag_ShouldReturnConditionalTemplate() {
        String result = thymeleafController.conditional(model, true);
        assertEquals("conditional", result);
    }

    @Test
    void testConditional_WithFalseFlag_ShouldReturnConditionalTemplate() {
        String result = thymeleafController.conditional(model, false);
        assertEquals("conditional", result);
    }

    @Test
    void testControllerInstantiation() {
        assertNotNull(thymeleafController);
    }
}
