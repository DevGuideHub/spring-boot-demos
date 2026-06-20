package io.github.cunyu1943.demosbeetl.config;

import org.beetl.core.ResourceLoader;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring6.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring6.BeetlSpringViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Beetl 配置类
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: BeetlConfig
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Configuration
public class BeetlConfig {

    /**
     * Beetl GroupTemplate 配置
     *
     * @return BeetlGroupUtilConfiguration
     */
    @Bean(initMethod = "init")
    public BeetlGroupUtilConfiguration beetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration configuration = new BeetlGroupUtilConfiguration();
        ResourceLoader resourceLoader = new ClasspathResourceLoader(
                Thread.currentThread().getContextClassLoader(), "templates");
        configuration.setResourceLoader(resourceLoader);
        return configuration;
    }

    /**
     * Beetl 视图解析器
     *
     * @param configuration Beetl配置
     * @return BeetlSpringViewResolver
     */
    @Bean
    public BeetlSpringViewResolver beetlSpringViewResolver(BeetlGroupUtilConfiguration configuration) {
        BeetlSpringViewResolver viewResolver = new BeetlSpringViewResolver();
        viewResolver.setOrder(0);
        viewResolver.setConfig(configuration);
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setSuffix(".btl");
        return viewResolver;
    }

}