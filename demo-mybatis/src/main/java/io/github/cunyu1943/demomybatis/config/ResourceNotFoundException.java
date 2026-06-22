package io.github.cunyu1943.demomybatis.config;

/**
 * @description: 资源不存在异常
 * @author: cunyu1943
 * @date: 2026-06-22
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + "不存在：id=" + id);
    }
}