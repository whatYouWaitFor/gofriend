package com.greg.gofriend.exception;

import com.greg.gofriend.common.ErrorCode;

/**
 * 自定义异常类
 * @author greg
 * @version 2023/4/11
 **/
public class BusinessException extends RuntimeException {

    private final int code;

    private final String message;

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
