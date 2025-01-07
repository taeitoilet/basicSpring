package com.fintech.tech_clone.exception;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}