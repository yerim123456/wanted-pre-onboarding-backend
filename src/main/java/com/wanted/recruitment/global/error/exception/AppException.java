package com.wanted.recruitment.global.error.exception;

import com.wanted.recruitment.global.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {
	private ErrorCode errorCode;
	private String message;

	public AppException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
	}
}
