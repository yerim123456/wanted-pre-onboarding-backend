package com.wanted.recruitment.Apply.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.wanted.recruitment.global.common.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Apply", description = "지원 관련 API")
public interface ApplyControllerDocs {
	@Operation(summary = "채용공고 지원", description = "사용자 ID와 채용공고 ID를 기반으로 채용공고에 지원합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "지원이 성공적으로 되었습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
			)
		),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 400, \"message\": \"회사는 사용자 권한에 접근할 수 없습니다.\" }, " +
					"{ \"code\": 400, \"message\": \"해당 채용공고 지원 기간이 아닙니다.\" } " +
					"]"
				)
			)
		),
		@ApiResponse(responseCode = "404", description = "필요한 값을 찾을 수 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 404, \"message\": \"해당하는 사용자가 없습니다.\" }, " +
					"{ \"code\": 404, \"message\": \"해당하는 채용공고가 없습니다.\" } " +
					"]"
				)
			)
		),
		@ApiResponse(responseCode = "409", description = "해당 채용공고에 이미 지원 이력이 존재합니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 409, \"message\": \"해당 채용공고에 이미 지원 이력이 존재합니다.\" }")
			)
		)
	})
	ResponseEntity<ResponseDto> saveApply(@RequestParam Long userId, @PathVariable Long employmentId);

}
