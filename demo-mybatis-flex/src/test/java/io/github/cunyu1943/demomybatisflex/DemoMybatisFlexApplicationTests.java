package io.github.cunyu1943.demomybatisflex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @description: 启动类单元测试
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: DemoMybatisFlexApplicationTests
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

class DemoMybatisFlexApplicationTests {

    @Test
    void testApplicationClassExists() {
        assertDoesNotThrow(() -> DemoMybatisFlexApplication.class.getName());
    }

}