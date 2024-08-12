package com.wanted.recruitment.Employment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.recruitment.Employment.dto.req.EmploymentReqDto;
import com.wanted.recruitment.Employment.dto.res.EmploymentDetailResDto;
import com.wanted.recruitment.Employment.dto.res.EmploymentItemResDto;
import com.wanted.recruitment.Employment.service.EmploymentService;
import com.wanted.recruitment.global.common.DataResponseDto;
import com.wanted.recruitment.global.common.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment")
public class EmploymentController implements EmploymentControllerDocs {
	private final EmploymentService employmentService;

	// 채용공고 등록
	@PostMapping
	public ResponseEntity<ResponseDto> createEmployment(@RequestParam Long userId,
		@RequestBody @Valid EmploymentReqDto employmentReqDto) {
		employmentService.saveEmployment(userId, employmentReqDto);
		return ResponseEntity.ok(ResponseDto.of(201));
	}

	// 채용공고 수정
	@PatchMapping("/{employmentId}")
	public ResponseEntity<ResponseDto> updateEmployment(@RequestParam Long userId, @PathVariable Long employmentId,
		@RequestBody @Valid EmploymentReqDto employmentReqDto) {
		employmentService.updateEmployment(userId, employmentId, employmentReqDto);
		return ResponseEntity.ok(ResponseDto.of(200));
	}

	// 채용공고 삭제
	@DeleteMapping("/{employmentId}")
	public ResponseEntity<ResponseDto> deleteEmployment(@RequestParam Long userId, @PathVariable Long employmentId) {
		employmentService.deleteEmployment(userId, employmentId);
		return ResponseEntity.ok(ResponseDto.of(200));
	}

	// 채용공고 전체 조회
	@GetMapping
	public ResponseEntity<ResponseDto> getAllEmployments() {
		List<EmploymentItemResDto> employmentItemResDtos = employmentService.readAllEmploymentDesc();
		return ResponseEntity.status(200).body(DataResponseDto.of(employmentItemResDtos, 200));
	}

	// 채용공고 키워드 검색
	@GetMapping("/search")
	public ResponseEntity<ResponseDto> searchEmployments(@RequestParam String keyword) {
		List<EmploymentItemResDto> employmentItemResDtos = employmentService.readEmploymentsByKeyword(keyword);
		return ResponseEntity.status(200).body(DataResponseDto.of(employmentItemResDtos, 200));
	}

	// 채용공고 상세 조회
	@GetMapping("/{employmentId}")
	public ResponseEntity<ResponseDto> getEmploymentDetail(@PathVariable Long employmentId) {
		EmploymentDetailResDto employmentDetailResDto = employmentService.readEmploymentDetail(employmentId);
		return ResponseEntity.status(200).body(DataResponseDto.of(employmentDetailResDto, 200));
	}

}
