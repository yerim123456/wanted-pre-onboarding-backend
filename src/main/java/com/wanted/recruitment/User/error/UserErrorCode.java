package com.wanted.recruitment.User.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.wanted.recruitment.global.error.ErrorCode;

@Getter
public enum UserErrorCode implements ErrorCode {
    USER_NOT_EXIST(HttpStatus.NOT_FOUND, "해당하는 사용자가 없습니다."),
    INVALID_USER_TYPE(HttpStatus.BAD_REQUEST, "해당하는 사용자 권한이 없습니다."),
    CAN_NOT_ACCESS_TO_COMPANY_TYPE(HttpStatus.BAD_REQUEST, "사용자는 회사 권한에 접근할 수 없습니다."),
    COMPANY_NOT_EXIST(HttpStatus.NOT_FOUND, "해당하는 회사가 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
