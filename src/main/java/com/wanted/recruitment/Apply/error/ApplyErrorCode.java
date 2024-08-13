package com.wanted.recruitment.Apply.error;

import org.springframework.http.HttpStatus;

import com.wanted.recruitment.global.error.ErrorCode;

import lombok.Getter;

@Getter
public enum ApplyErrorCode implements ErrorCode {
    CAN_NOT_APPLY_TO_SAME_EMPLOYMENT(HttpStatus.CONFLICT, "해당 채용공고에 이미 지원 이력이 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ApplyErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
