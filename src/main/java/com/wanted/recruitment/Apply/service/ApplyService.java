package com.wanted.recruitment.Apply.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.recruitment.Apply.domain.entity.Apply;
import com.wanted.recruitment.Apply.domain.repository.ApplyRepository;
import com.wanted.recruitment.Apply.error.ApplyErrorCode;
import com.wanted.recruitment.Employment.domain.entity.Employment;
import com.wanted.recruitment.Employment.service.EmploymentService;
import com.wanted.recruitment.User.domain.entity.User;
import com.wanted.recruitment.User.service.UserService;
import com.wanted.recruitment.global.error.exception.AppException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplyService {
	private final ApplyRepository applyRepository;
	private final UserService userService;
	private final EmploymentService employmentService;

	// 채용공고 지원 저장
	@Transactional
	public void saveApply(Long userId, Long employmentId) {
		// 일반 사용자 유효성 검증
		User user = userService.checkUserIsValidate(userId);

		// 채용공고 유효성 검증
		Employment employment = employmentService.checkEmploymentIsValidate(employmentId);

		// 사용자 해당 채용 공고에 지원 이력이 있는지 확인
		if (applyRepository.existsByUserIdAndEmploymentId(userId, employmentId)) {
			throw new AppException(ApplyErrorCode.CAN_NOT_APPLY_TO_SAME_EMPLOYMENT);
		}

		// 저장
		applyRepository.save(
			Apply.builder()
				.user(user)
				.employment(employment)
				.build()
		);
	}
}
