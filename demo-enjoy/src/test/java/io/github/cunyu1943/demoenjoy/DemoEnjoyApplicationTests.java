package io.github.cunyu1943.demoenjoy;

import org.junit.jupiter.api.Test;

/**
 * @description: Enjoy 演示应用测试类
 * @author: cunyu1943
 * @date: 2026-06-20
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */
class DemoEnjoyApplicationTests {

    @Test
    void applicationClassExists() {
        // 验证主类存在
        try {
            Class.forName("io.github.cunyu1943.demoenjoy.DemoEnjoyApplication");
        } catch (ClassNotFoundException e) {
            throw new AssertionError("DemoEnjoyApplication class should exist", e);
        }
    }

}
