package io.github.cunyu1943.demoenjoy.config;

import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * @description: Enjoy 模板引擎配置类
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: EnjoyConfig
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Configuration
public class EnjoyConfig {

    /**
     * 配置 Enjoy 引擎
     *
     * @return Enjoy Engine 实例
     */
    @Bean
    public Engine enjoyEngine() {
        Engine engine = new Engine();
        // 设置模板加载路径
        engine.setSourceFactory(new ClassPathSourceFactory());
        engine.setBaseTemplatePath("templates");
        // 关闭 devMode 以避免 Java Compiler 兼容性问题
        engine.setDevMode(false);
        return engine;
    }

    /**
     * 配置 Enjoy 视图解析器
     *
     * @param engine Enjoy 引擎
     * @return 视图解析器
     */
    @Bean
    public ViewResolver enjoyViewResolver(Engine engine) {
        return new AbstractTemplateViewResolver() {
            {
                setOrder(0);
                setViewClass(EnjoyView.class);
                setPrefix("");
                setSuffix(".html");
            }

            @Override
            protected Class<?> requiredViewClass() {
                return EnjoyView.class;
            }

            @Override
            protected AbstractUrlBasedView buildView(String viewName) throws Exception {
                EnjoyView view = (EnjoyView) super.buildView(viewName);
                view.setEngine(engine);
                return view;
            }
        };
    }

    /**
     * Enjoy 视图实现类
     */
    public static class EnjoyView extends AbstractTemplateView {
        private Engine engine;

        public void setEngine(Engine engine) {
            this.engine = engine;
        }

        @Override
        protected void renderMergedTemplateModel(java.util.Map<String, Object> model,
                                                 jakarta.servlet.http.HttpServletRequest request,
                                                 jakarta.servlet.http.HttpServletResponse response) throws Exception {
            response.setContentType(getContentType());
            response.setCharacterEncoding("UTF-8");
            String viewName = getUrl();
            engine.getTemplate(viewName).render(model, response.getWriter());
        }
    }

}
