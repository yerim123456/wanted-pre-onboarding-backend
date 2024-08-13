package com.wanted.recruitment.Employment.error;

import org.springframework.http.HttpStatus;

import com.wanted.recruitment.global.error.ErrorCode;

import lombok.Getter;

@Getter
public enum EmploymentErrorCode implements ErrorCode {
    EMPLOYMENT_NOT_EXIST(HttpStatus.NOT_FOUND, "해당하는 채용공고가 없습니다."),
    INVALID_ACCESS_TO_EMPLOYMENT(HttpStatus.BAD_REQUEST, "해당 회사가 작성한 채용공고가 아닙니다."),
    INVALID_DATE_PERIOD(HttpStatus.BAD_REQUEST, "시작 일시는 마감 일시보다 이전이거나 같아야 합니다."),
    CAN_NOT_SAVE_SAME_EMPLOYMENT(HttpStatus.CONFLICT, "해당 회사, 직책의 채용공고가 존재하며, 아직 마감되지 않았습니다."),
    EMPLOYMENT_LIST_NOT_EXIST(HttpStatus.NOT_FOUND, "조회할 채용공고가 없습니다."),
    KEYWORD_EMPLOYMENT_LIST_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 키워드에 해당하는 채용공고가 없습니다."),
    INVALID_EMPLOYMENT_APPLY_PERIOD(HttpStatus.BAD_REQUEST, "해당 채용공고 지원 기간이 아닙니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    EmploymentErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
