package com.wanted.recruitment.User.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.recruitment.User.domain.entity.User;
import com.wanted.recruitment.User.domain.repository.UserRepository;
import com.wanted.recruitment.User.error.UserErrorCode;
import com.wanted.recruitment.User.model.UserType;
import com.wanted.recruitment.global.error.exception.AppException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public User checkCompanyUserIsValidate(Long userId) {
		// 유효한 사용자인지 체크
		User companyUser = userRepository.findById(userId)
			.orElseThrow(() -> new AppException(UserErrorCode.AUTHORIZATION_FAILED));

		// 사용자 타입이 회사인지 체크
		if (!companyUser.getUserType().equals(UserType.COMPANY)) {
			throw new AppException(UserErrorCode.CAN_NOT_ACCESS_TO_COMPANY_TYPE);
		}

		// 회사 정보 존재하는지 체크
		if (companyUser.getCompany() == null) {
			throw new AppException(UserErrorCode.COMPANY_NOT_EXIST);
		}

		return companyUser;
	}
}
