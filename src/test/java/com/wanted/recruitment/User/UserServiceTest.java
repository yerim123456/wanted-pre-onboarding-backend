package com.wanted.recruitment.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wanted.recruitment.User.domain.entity.User;
import com.wanted.recruitment.User.domain.repository.UserRepository;
import com.wanted.recruitment.User.error.UserErrorCode;
import com.wanted.recruitment.User.service.UserService;
import com.wanted.recruitment.global.error.exception.AppException;
import com.wanted.recruitment.setup.MockEntityFactory;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User mockCompanyUser;

	private User mockUser;

	@BeforeEach
	void setUp() {
		mockCompanyUser = MockEntityFactory.createMockCompanyUser();
		mockUser = MockEntityFactory.createMockUser();
	}

	@Nested
	@DisplayName("회사 사용자 유효성 검사")
	class ValidateCompanyAndCompanyUserTest {

		@Test
		@DisplayName("[성공] 사용자가 존재하며, 회사 정보가 존재하는 회사 사용자인 경우 유효성 검사에 성공한다")
		void shouldNotThrowExceptionWhenCompanyUserValidate() {
			// Given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(mockCompanyUser));

			// When
			User result = userService.checkCompanyUserIsValidate(1L);

			// Then
			assertEquals(mockCompanyUser, result);
		}

		@Test
		@DisplayName("[실패] 사용자 미존재로 인해 예외를 발생시킨다.")
		void shouldThrowExceptionWhenUserNotFound() {
			// Given
			given(userRepository.findById(anyLong())).willReturn(Optional.empty());

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> userService.checkCompanyUserIsValidate(1L));
			assertEquals(UserErrorCode.USER_NOT_EXIST, exception.getErrorCode());
		}

		@Test
		@DisplayName("[실패] 사용자 타입이 회사가 아니기에 예외를 발생시킨다.")
		void shouldThrowExceptionWhenUserTypeIsNotCompany() {
			// Given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(mockUser));

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> userService.checkCompanyUserIsValidate(1L));
			assertEquals(UserErrorCode.CAN_NOT_ACCESS_TO_COMPANY_TYPE, exception.getErrorCode());
		}

		@Test
		@DisplayName("[실패] 회사 사용자이나, 회사 정보가 없어 예외를 발생시킨다.")
		void shouldThrowExceptionWhenCompanyIsNotExist() {
			// Given
			mockCompanyUser.updateUserTypeToCompany(null);
			given(userRepository.findById(anyLong())).willReturn(Optional.of(mockCompanyUser));

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> userService.checkCompanyUserIsValidate(1L));
			assertEquals(UserErrorCode.COMPANY_NOT_EXIST, exception.getErrorCode());
		}
	}

	@Nested
	@DisplayName("일반 사용자 유효성 검사")
	class ValidateUserTest {

		@Test
		@DisplayName("[성공] 사용자가 존재하며, 일반 사용자인 경우 유효성 검사에 성공한다")
		void shouldNotThrowExceptionWhenUserValidate() {
			// Given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(mockUser));

			// When
			User result = userService.checkUserIsValidate(1L);

			// Then
			assertEquals(mockUser, result);
		}

		@Test
		@DisplayName("[실패] 사용자 타입이 사용자가 아니기에 예외를 발생시킨다.")
		void shouldThrowExceptionWhenUserTypeIsNotCompany() {
			// Given
			given(userRepository.findById(anyLong())).willReturn(Optional.of(mockCompanyUser));

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> userService.checkUserIsValidate(1L));
			assertEquals(UserErrorCode.CAN_NOT_ACCESS_TO_USER_TYPE, exception.getErrorCode());
		}
	}
}