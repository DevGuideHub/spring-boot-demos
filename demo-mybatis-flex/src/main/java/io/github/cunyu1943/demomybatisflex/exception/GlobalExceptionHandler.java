package io.github.cunyu1943.demomybatisflex.exception;

import io.github.cunyu1943.demomybatisflex.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 全局异常处理器
 * @author: cunyu1943
 * @date: 2026-06-22
 * @fileName: GlobalExceptionHandler
 * @version: 1.0
 * 公众号：村雨遥
 * GitHub: https://github.com/cunyu1943
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理 IllegalArgumentException
     *
     * @param ex 异常
     * @return 统一响应结果
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("参数异常：{}", ex.getMessage());
        return ResponseEntity.badRequest().body(Result.fail(400, ex.getMessage()));
    }

    /**
     * 处理 RuntimeException
     *
     * @param ex 异常
     * @return 统一响应结果
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Result<Void>> handleRuntimeException(RuntimeException ex) {
        logger.error("运行时异常：{}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.fail(500, ex.getMessage()));
    }

    /**
     * 处理 Exception
     *
     * @param ex 异常
     * @return 统一响应结果
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleException(Exception ex) {
        logger.error("系统异常：{}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.fail(500, "系统内部错误"));
    }
}
