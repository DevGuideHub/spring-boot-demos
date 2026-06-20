package io.github.cunyu1943.demomybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: MyBatis Plus 启动类
 * @author: cunyu1943
 * @date: 2026-06-19
 * @fileName: DemoMybatisPlusApplication
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@SpringBootApplication
@MapperScan("io.github.cunyu1943.demomybatisplus.mapper")
public class DemoMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisPlusApplication.class, args);
    }

}
