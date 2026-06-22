package io.github.cunyu1943.demomybatisplus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoMybatisPlusApplicationTests.TestApplication.class)
class DemoMybatisPlusApplicationTests {

    @Test
    void contextLoads() {
    }

    @SpringBootApplication
    static class TestApplication {
    }
}