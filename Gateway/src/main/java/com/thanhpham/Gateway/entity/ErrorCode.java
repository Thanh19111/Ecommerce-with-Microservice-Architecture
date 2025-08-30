package com.thanhpham.Gateway.entity;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, 401, "Invalid or empty token"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "You don't have permission");

    private final HttpStatus statusCode;
    private final Integer code;
    private final String message;

    ErrorCode(HttpStatus statusCode, Integer code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
