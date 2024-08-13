package com.wanted.recruitment.Employment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.wanted.recruitment.Employment.dto.req.EmploymentReqDto;
import com.wanted.recruitment.global.common.DataResponseDto;
import com.wanted.recruitment.global.common.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Employment", description = "채용공고 관련 API")
public interface EmploymentControllerDocs {
	@Operation(summary = "채용공고 등록", description = "사용자 ID를 기반으로 채용공고를 등록합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "채용공고가 성공적으로 등록되었습니다.",
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
					"{ \"code\": 400, \"message\": \"사용자는 회사 권한에 접근할 수 없습니다.\" }, " +
					"{ \"code\": 400, \"message\": \"채용하는 직책 입력이 필요합니다.\" }," +
					"{ \"code\": 400, \"message\": \"채용공고 내용은 10000자 이하여야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"기술은 500자 이하여야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"시작 일시는 마감 일시보다 이전이거나 같아야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"채용 시작일 입력이 필요합니다.\" }," +
					"{ \"code\": 400, \"message\": \"채용 마감일 입력이 필요합니다.\" }" +
					"]"
				)
			)
		),
		@ApiResponse(responseCode = "404", description = "해당하는 회사가 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 회사가 없습니다.\" }")
			)
		),
		@ApiResponse(responseCode = "409", description = "해당 회사, 직책의 채용공고가 존재하며, 아직 마감되지 않았습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 409, \"message\": \"해당 회사, 직책의 채용공고가 존재하며, 아직 마감되지 않았습니다.\" }")
			)
		)
	})
	ResponseEntity<ResponseDto> saveEmployment(@RequestParam Long userId,
		@RequestBody @Valid EmploymentReqDto employmentReqDto);

	@Operation(summary = "채용공고 수정", description = "채용공고 ID를 기반으로 채용공고를 수정합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채용공고가 성공적으로 수정되었습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
			)
		),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 400, \"message\": \"사용자는 회사 권한에 접근할 수 없습니다.\" }, " +
					"{ \"code\": 400, \"message\": \"해당 회사가 작성한 채용공고가 아닙니다.\" }," +
					"{ \"code\": 400, \"message\": \"채용하는 직책 입력이 필요합니다.\" }," +
					"{ \"code\": 400, \"message\": \"채용공고 내용은 10000자 이하여야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"기술은 500자 이하여야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"시작 일시는 마감 일시보다 이전이거나 같아야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"채용 시작일 입력이 필요합니다.\" }," +
					"{ \"code\": 400, \"message\": \"채용 마감일 입력이 필요합니다.\" }" +
					"]"
				)
			)
		),
		@ApiResponse(responseCode = "404", description = "필요한 값을 찾을 수 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 404, \"message\": \"해당하는 회사가 없습니다.\" } ," +
					"{ \"code\": 404, \"message\": \"해당하는 채용공고가 없습니다.\" }" +
					"]"
				)
			)
		)
	})
	ResponseEntity<ResponseDto> updateEmployment(@RequestParam Long userId, @PathVariable Long employmentId,
		@RequestBody @Valid EmploymentReqDto employmentReqDto);

	@Operation(summary = "채용공고 삭제", description = "채용공고 ID를 기반으로 채용공고를 삭제합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채용공고가 성공적으로 삭제되었습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
			)
		),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 400, \"message\": \"사용자는 회사 권한에 접근할 수 없습니다.\" } ," +
					"{ \"code\": 400, \"message\": \"해당 회사가 작성한 채용공고가 아닙니다.\" }" +
					"]"
				)
			)
		),
		@ApiResponse(responseCode = "404", description = "필요한 값을 찾을 수 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 404, \"message\": \"해당하는 회사가 없습니다.\" }," +
					"{ \"code\": 404, \"message\": \"해당하는 채용공고가 없습니다.\" }" +
					"]"
				)
			)
		)
	})
	ResponseEntity<ResponseDto> deleteEmployment(@RequestParam Long userId, @PathVariable Long employmentId);

	@Operation(summary = "채용공고 전체 조회", description = "등록된 모든 채용공고를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "성공적으로 조회되었습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = DataResponseDto.class),
				examples = @ExampleObject(value = "{\"code\": 200, \"message\": \"OK\", \"data\": [{\"employmentId\": 2, \"companyName\": \"Wanted\", \"country\": \"South Korea\", \"region\": \"Seoul\", \"position\": \"Backend Developer\", \"compensation\": 100000000, \"skill\": \"Java\"}, {\"employmentId\": 1, \"companyName\": \"Wanted\", \"country\": \"South Korea\", \"region\": \"Seoul\", \"position\": \"Frontend Developer\", \"compensation\": 80000000, \"skill\": \"React\"}]}"))
		), @ApiResponse(responseCode = "404", description = "조회할 채용공고가 없습니다.",
		content = @Content(
			mediaType = "application/json",
			schema = @Schema(implementation = ResponseDto.class),
			examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"조회할 채용공고가 없습니다.\" }")
		)
	)
	})
	ResponseEntity<ResponseDto> readAllEmployments();

	@Operation(summary = "채용공고 키워드 검색", description = "키워드를 통해 채용공고를 검색합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채용공고를 성공적으로 검색했습니다.", content = @Content(
			mediaType = "application/json",
			schema = @Schema(implementation = DataResponseDto.class),
			examples = @ExampleObject(value = "{\"code\": 200, \"message\": \"OK\", \"data\": [{\"employmentId\": 3, \"companyName\": \"Wanted\", \"country\": \"South Korea\", \"region\": \"Seoul\", \"position\": \"Spring Backend Developer\", \"compensation\": 120000000, \"skill\": \"Spring\"}, {\"employmentId\": 6, \"companyName\": \"Naver\", \"country\": \"South Korea\", \"region\": \"Seoul\", \"position\": \"Backend Developer\", \"compensation\": 120000000, \"skill\": \"Spring\"}]}"))
		),
		@ApiResponse(responseCode = "404", description = "해당 키워드에 해당하는 채용공고가 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당 키워드에 해당하는 채용공고가 없습니다.\" }")
			)
		)
	})
	ResponseEntity<ResponseDto> searchEmployments(@RequestParam String keyword);

	@Operation(summary = "채용공고 상세 조회", description = "채용공고 ID를 기반으로 상세 정보를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채용공고 상세 정보를 성공적으로 조회했습니다.", content = @Content(
			mediaType = "application/json",
			schema = @Schema(implementation = DataResponseDto.class),
			examples = @ExampleObject(value = "{\"code\": 200, \"message\": \"OK\", \"data\": {\"employmentId\": 4, \"companyName\": \"Wanted\", \"country\": \"South Korea\", \"region\": \"Seoul\", \"position\": \"DevOps Engineer\", \"compensation\": 110000000, \"skill\": \"AWS\", \"content\": \"Responsible for managing and optimizing AWS infrastructure.\", \"otherEmploymentIds\": [1, 2, 3]}}"))
		),
		@ApiResponse(responseCode = "404", description = "해당하는 채용공고가 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 채용공고가 없습니다.\" }")
			)
		)
	})
	ResponseEntity<ResponseDto> readEmploymentDetail(@PathVariable Long employmentId);

}
