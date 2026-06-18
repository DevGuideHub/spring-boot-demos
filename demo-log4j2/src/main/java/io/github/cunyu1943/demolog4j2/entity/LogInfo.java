package io.github.cunyu1943.demolog4j2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 日志信息实体类
 * @author: cunyu1943
 * @date: 2026-06-18
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInfo {

    /** 日志级别 */
    private String level;

    /** 日志消息 */
    private String message;

    /** 日志时间 */
    private LocalDateTime timestamp;

    /** 线程名 */
    private String threadName;
}