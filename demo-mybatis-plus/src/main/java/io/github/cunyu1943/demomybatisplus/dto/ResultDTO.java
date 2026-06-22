package io.github.cunyu1943.demomybatisplus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 统一响应结果
 * @author: cunyu1943
 * @date: 2026-06-22
 * @version: 1.0
 *           公众号：村雨遥
 *           GitHub: https://github.com/cunyu1943
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> {

    /** 响应码 */
    private Integer respCode;

    /** 响应消息 */
    private String respMsg;

    /** 响应数据 */
    private T data;

    /**
     * 成功响应
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应结果
     */
    public static <T> ResultDTO<T> success(T data) {
        return ResultDTO.<T>builder()
                .respCode(200)
                .respMsg("操作成功")
                .data(data)
                .build();
    }

    /**
     * 成功响应（带消息）
     *
     * @param data    数据
     * @param respMsg 消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResultDTO<T> success(T data, String respMsg) {
        return ResultDTO.<T>builder()
                .respCode(200)
                .respMsg(respMsg)
                .data(data)
                .build();
    }

    /**
     * 创建成功响应
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应结果
     */
    public static <T> ResultDTO<T> created(T data) {
        return ResultDTO.<T>builder()
                .respCode(201)
                .respMsg("创建成功")
                .data(data)
                .build();
    }

    /**
     * 创建成功响应（带消息）
     *
     * @param data    数据
     * @param respMsg 消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResultDTO<T> created(T data, String respMsg) {
        return ResultDTO.<T>builder()
                .respCode(201)
                .respMsg(respMsg)
                .data(data)
                .build();
    }

    /**
     * 失败响应
     *
     * @param respCode 错误码
     * @param respMsg  错误消息
     * @param <T>      数据类型
     * @return 响应结果
     */
    public static <T> ResultDTO<T> error(Integer respCode, String respMsg) {
        return ResultDTO.<T>builder()
                .respCode(respCode)
                .respMsg(respMsg)
                .data(null)
                .build();
    }

    /**
     * 参数错误响应
     *
     * @param respMsg 错误消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResultDTO<T> badRequest(String respMsg) {
        return error(400, respMsg);
    }

    /**
     * 资源不存在响应
     *
     * @param respMsg 错误消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResultDTO<T> notFound(String respMsg) {
        return error(404, respMsg);
    }

    /**
     * 服务器错误响应
     *
     * @param respMsg 错误消息
     * @param <T>     数据类型
     * @return 响应结果
     */
    public static <T> ResultDTO<T> serverError(String respMsg) {
        return error(500, respMsg);
    }
}