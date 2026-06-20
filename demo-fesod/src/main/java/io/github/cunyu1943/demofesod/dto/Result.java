package io.github.cunyu1943.demofesod.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 统一响应结果
 * @author: cunyu1943
 * @date: 2026-06-20
 * @fileName: Result
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 响应码
     */
    private Integer respCode;

    /**
     * 响应消息
     */
    private String respMsg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应时间
     */
    private LocalDateTime timestamp;

    /**
     * 成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return 成功响应结果
     */
    public static <T> Result<T> success() {
        return Result.<T>builder()
                .respCode(200)
                .respMsg("操作成功")
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 成功响应（有数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 成功响应结果
     */
    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .respCode(200)
                .respMsg("操作成功")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 失败响应
     *
     * @param respMsg 响应消息
     * @param <T>     数据类型
     * @return 失败响应结果
     */
    public static <T> Result<T> fail(String respMsg) {
        return Result.<T>builder()
                .respCode(500)
                .respMsg(respMsg)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 失败响应（自定义状态码）
     *
     * @param respCode 响应码
     * @param respMsg  响应消息
     * @param <T>      数据类型
     * @return 失败响应结果
     */
    public static <T> Result<T> fail(Integer respCode, String respMsg) {
        return Result.<T>builder()
                .respCode(respCode)
                .respMsg(respMsg)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
