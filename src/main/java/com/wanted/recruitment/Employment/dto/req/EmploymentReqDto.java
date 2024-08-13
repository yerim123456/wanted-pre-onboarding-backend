package com.wanted.recruitment.Employment.dto.req;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmploymentReqDto {
	@NotEmpty(message = "채용하는 직책 입력이 필요합니다.")
	private String position;

	private Integer compensation;

	@Size(max = 10000, message = "채용공고 내용은 10000자 이하여야 합니다.")
	private String content;

	@Size(max = 500, message = "기술은 500자 이하여야 합니다.")
	private String skill;

	@NotNull(message = "채용 시작일 입력이 필요합니다.")
	private LocalDateTime startDate;

	@NotNull(message = "채용 마감일 입력이 필요합니다.")
	private LocalDateTime endDate;
}
