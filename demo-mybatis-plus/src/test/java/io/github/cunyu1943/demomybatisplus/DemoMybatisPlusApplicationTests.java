package io.github.cunyu1943.demomybatisplus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description: Spring Boot 应用测试
 * @author: cunyu1943
 * @date: 2026-06-19
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@SpringBootTest(classes = DemoMybatisPlusApplicationTests.TestApplication.class)
class DemoMybatisPlusApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 测试用的简化应用配置，排除数据源自动配置
     */
    @SpringBootApplication(exclude = {
            org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
            org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration.class,
            com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration.class
    })
    static class TestApplication {
    }

}
