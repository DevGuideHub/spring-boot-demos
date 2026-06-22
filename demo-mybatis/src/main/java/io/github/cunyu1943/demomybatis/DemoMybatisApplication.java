package io.github.cunyu1943.demomybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: MyBatis 启动类
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: DemoMybatisApplication
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@SpringBootApplication
@MapperScan("io.github.cunyu1943.demomybatis.mapper")
public class DemoMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisApplication.class, args);
    }

}