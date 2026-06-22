package io.github.cunyu1943.demomybatisflex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 启动类
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: DemoMybatisFlexApplication
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */

@SpringBootApplication
@MapperScan("io.github.cunyu1943.demomybatisflex.mapper")
public class DemoMybatisFlexApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisFlexApplication.class, args);
    }

}
