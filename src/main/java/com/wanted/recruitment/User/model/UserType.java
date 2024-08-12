package com.wanted.recruitment.User.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
	USER("ROLE_USER", "일반 사용자"),
	COMPANY("ROLE_COMPANY", "회사"),
	;

	private final String key;
	private final String title;
}
