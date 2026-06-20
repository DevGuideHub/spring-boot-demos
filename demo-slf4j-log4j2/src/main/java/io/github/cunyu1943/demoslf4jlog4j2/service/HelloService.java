package io.github.cunyu1943.demoslf4jlog4j2.service;

import io.github.cunyu1943.demoslf4jlog4j2.dto.Result;

/**
 * @description: Hello服务接口
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: HelloService
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
public interface HelloService {

    /**
     * 获取Hello消息
     *
     * @return Hello消息
     */
    Result<String> getHello();

    /**
     * 获取自定义消息
     *
     * @param name 名称
     * @return 自定义消息
     */
    Result<String> getCustomMessage(String name);

    /**
     * 测试错误日志
     *
     * @return 错误日志测试结果
     */
    Result<String> testErrorLog();

}