
package io.github.cunyu1943.demoh2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    /**
     * 响应码（200=成功，500=失败）
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
     * 默认构造函数
     */
    public Result() {
    }

    /**
     * 全参构造函数
     */
    public Result(Integer respCode, String respMsg, T data, LocalDateTime timestamp) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.data = data;
        this.timestamp = timestamp;
    }

    /**
     * 成功响应（带数据）
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data, LocalDateTime.now());
    }

    /**
     * 成功响应（不带数据）
     *
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null, LocalDateTime.now());
    }

    /**
     * 失败响应
     *
     * @param respMsg 失败消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> Result<T> fail(String respMsg) {
        return new Result<>(500, respMsg, null, LocalDateTime.now());
    }

    /**
     * 失败响应（带响应码）
     *
     * @param respCode 响应码
     * @param respMsg  失败消息
     * @param <T>      数据类型
     * @return 响应结果
     */
    public static <T> Result<T> fail(Integer respCode, String respMsg) {
        return new Result<>(respCode, respMsg, null, LocalDateTime.now());
    }

    // Getters and Setters
    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
