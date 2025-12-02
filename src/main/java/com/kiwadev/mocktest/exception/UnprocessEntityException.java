package com.kiwadev.mocktest.exception;

import lombok.Getter;

@Getter
public class UnprocessEntityException extends RuntimeException {
    private final ErrorCode errorCode;
    public UnprocessEntityException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
