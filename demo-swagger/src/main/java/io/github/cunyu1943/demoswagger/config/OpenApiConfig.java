package io.github.cunyu1943.demoswagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Swagger/OpenAPI 配置类
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: OpenApiConfig
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Configuration
public class OpenApiConfig {

    /**
     * 配置 OpenAPI 信息
     *
     * @return OpenAPI 实例
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swagger3 API")
                        .description("Spring Boot 集成 Swagger3 示例接口文档")
                        .version("1.0")
                        .contact(new Contact()
                                .name("村雨遥")
                                .url("https://github.com/cunyu1943")
                                .email("cunyu1943@example.com")));
    }

}