package com.wanted.recruitment.Apply.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.recruitment.Apply.service.ApplyService;
import com.wanted.recruitment.global.common.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/apply")
public class ApplyController implements ApplyControllerDocs {
	private final ApplyService applyService;

	// 채용공고 지원 저장
	@PostMapping("/{employmentId}")
	public ResponseEntity<ResponseDto> saveApply(@RequestParam Long userId, @PathVariable Long employmentId) {
		applyService.saveApply(userId, employmentId);
		return ResponseEntity.ok(ResponseDto.of(201));
	}
}
