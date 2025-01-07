package com.fintech.tech_clone.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_KEY(1001,"Invalid message key",HttpStatus.INTERNAL_SERVER_ERROR),
    UNCATEGORIZED_EXCEPTION(9999,"uncategorized error",HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1003,"User existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003,"Username must be at least 3 characters",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1003,"Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1003,"Invalid Email",HttpStatus.BAD_REQUEST),
    INVALID_PHONE(1003,"Invalid Phone",HttpStatus.BAD_REQUEST),
    INVALID_CITIZEN(1003,"Invalid CITIZEN IDENTIFICATION",HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT(1003,"Amount of moneny is more than 1000",HttpStatus.BAD_REQUEST),
    INVALID_MONEY(1003,"Amount of moneny is more than 0",HttpStatus.BAD_REQUEST),
    INVALID_DOB(1003,"Invalid Date",HttpStatus.BAD_REQUEST),
    INVALID_ROLE(1003,"Invalid Role",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002,"User not existed",HttpStatus.NOT_FOUND),
    ROLE_NULL(1005,"Role must not null",HttpStatus.BAD_REQUEST),
    USERNAME_NULL(1005,"Username must not null",HttpStatus.BAD_REQUEST),
    PASSWORD_NULL(1005,"Password must not null",HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(1005,"Password wong",HttpStatus.BAD_REQUEST),
    CONTENT_NULL(1005,"Content must not null",HttpStatus.BAD_REQUEST),
    TITLE_NULL(1005,"Title must not null",HttpStatus.BAD_REQUEST),
    AUTHOR_NULL(1005,"Author must not null",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1001,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1004,"You dont have permission",HttpStatus.FORBIDDEN),
    PHONE_EXIST(1001,"phone existed",HttpStatus.BAD_REQUEST),
    CITIZEN_IDENTIFICATION_EXIST(1001,"CITIZEN IDENTIFICATION existed",HttpStatus.BAD_REQUEST),
    ;
    private int code;
    private String message;
    private HttpStatus statusCode;
}
