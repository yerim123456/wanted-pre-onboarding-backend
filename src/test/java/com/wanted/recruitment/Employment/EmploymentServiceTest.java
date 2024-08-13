package com.wanted.recruitment.Employment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wanted.recruitment.Employment.domain.entity.Employment;
import com.wanted.recruitment.Employment.domain.repository.EmploymentRepository;
import com.wanted.recruitment.Employment.dto.req.EmploymentReqDto;
import com.wanted.recruitment.Employment.dto.res.EmploymentDetailResDto;
import com.wanted.recruitment.Employment.dto.res.EmploymentItemResDto;
import com.wanted.recruitment.Employment.error.EmploymentErrorCode;
import com.wanted.recruitment.Employment.service.EmploymentService;
import com.wanted.recruitment.User.domain.entity.Company;
import com.wanted.recruitment.User.domain.entity.User;
import com.wanted.recruitment.User.service.UserService;
import com.wanted.recruitment.global.error.exception.AppException;
import com.wanted.recruitment.setup.MockEntityFactory;

@ExtendWith(MockitoExtension.class)
class EmploymentServiceTest {

	@Mock
	private EmploymentRepository employmentRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private EmploymentService employmentService;

	private User mockCompanyUser;
	private Employment mockEmployment;

	@BeforeEach
	void setUp() {
		mockCompanyUser = MockEntityFactory.createMockCompanyUser();
		mockEmployment = MockEntityFactory.createMockEmployment();

		lenient().when(userService.checkCompanyUserIsValidate(anyLong())).thenReturn(mockCompanyUser);
	}

	@Nested
	@DisplayName("채용공고 저장")
	class SaveEmployment {

		@Test
		@DisplayName("[성공] 채용공고를 저장한다.")
		void shouldSaveEmploymentSuccessfully() {
			// Given
			given(employmentRepository.existsByCompanyAndPositionAndActive(any(), any())).willReturn(false);
			given(employmentRepository.save(any(Employment.class))).willReturn(mockEmployment);

			EmploymentReqDto employmentReqDto = new EmploymentReqDto("Developer", 1000, "Job Description", "Java",
				LocalDateTime.now(), LocalDateTime.now().plusDays(10));

			// When & Then
			assertDoesNotThrow(() -> employmentService.saveEmployment(1L, employmentReqDto));
			verify(employmentRepository).save(any(Employment.class));
		}

		@Test
		@DisplayName("[실패] 이미 존재하는 채용공고로 인해 예외를 발생시킨다.")
		void shouldThrowExceptionWhenDuplicateEmploymentExists() {
			// Given
			given(employmentRepository.existsByCompanyAndPositionAndActive(any(), any())).willReturn(true);

			EmploymentReqDto employmentReqDto = new EmploymentReqDto("Developer", 1000, "Job Description", "Java",
				LocalDateTime.now(), LocalDateTime.now().plusDays(10));

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> employmentService.saveEmployment(1L, employmentReqDto));
			assertEquals(EmploymentErrorCode.CAN_NOT_SAVE_SAME_EMPLOYMENT.getMessage(), exception.getMessage());
		}
	}

	@Nested
	@DisplayName("채용공고 수정")
	class UpdateEmployment {

		@Test
		@DisplayName("[성공] 채용공고를 수정한다.")
		void shouldUpdateEmploymentSuccessfully() {
			// Given
			given(employmentRepository.findById(anyLong())).willReturn(Optional.of(mockEmployment));

			EmploymentReqDto employmentReqDto = new EmploymentReqDto("Updated Developer", 1500,
				"Updated Job Description", "Kotlin", LocalDateTime.now(), LocalDateTime.now().plusDays(15));

			// When & Then
			assertDoesNotThrow(() -> employmentService.updateEmployment(1L, 1L, employmentReqDto));
			verify(employmentRepository).save(any(Employment.class));
		}
	}

	@Nested
	@DisplayName("채용공고 삭제")
	class DeleteEmployment {

		@Test
		@DisplayName("[성공] 채용공고를 삭제한다.")
		void shouldDeleteEmploymentSuccessfully() {
			// Given
			given(employmentRepository.findById(anyLong())).willReturn(Optional.of(mockEmployment));

			// When & Then
			assertDoesNotThrow(() -> employmentService.deleteEmployment(1L, 1L));
			verify(employmentRepository).delete(mockEmployment);
		}
	}

	@Nested
	@DisplayName("채용공고 리스트 조회")
	class GetEmploymentList {

		@Test
		@DisplayName("[성공] 채용공고 리스트를 조회한다.")
		void shouldGetEmploymentListSuccessfully() {
			// Given
			EmploymentItemResDto employmentItemResDto = EmploymentItemResDto.builder().build();
			List<EmploymentItemResDto> employmentList = List.of(employmentItemResDto);
			given(employmentRepository.findAllEmploymentItemsOrderByCreatedAtDesc()).willReturn(
				Optional.of(employmentList));

			// When
			List<EmploymentItemResDto> result = employmentService.readAllEmploymentDesc();

			// Then
			assertFalse(result.isEmpty());
		}

		@Test
		@DisplayName("[실패] 조회한 채용공고 전체 리스트가 존재하지 않아 예외를 발생시킨다.")
		void shouldThrowExceptionWhenEmploymentKeywordListNotFound() {
			// Given
			given(employmentRepository.findAllEmploymentItemsOrderByCreatedAtDesc()).willReturn(
				Optional.empty());

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> employmentService.readAllEmploymentDesc());
			assertEquals(EmploymentErrorCode.EMPLOYMENT_LIST_NOT_EXIST, exception.getErrorCode());
		}
	}

	@Nested
	@DisplayName("채용공고 키워드 검색")
	class GetEmploymentKeywordList {

		@Test
		@DisplayName("[성공] 채용공고 키워드 리스트를 조회한다.")
		void shouldGetEmploymentKeywordListSuccessfully() {
			// Given
			EmploymentItemResDto employmentItemResDto = EmploymentItemResDto.builder().build();
			List<EmploymentItemResDto> employmentList = List.of(employmentItemResDto);
			given(employmentRepository.searchEmploymentItemsOrderByKeywordFrequency("keyword")).willReturn(
				Optional.of(employmentList));

			// When
			List<EmploymentItemResDto> result = employmentService.searchEmploymentsByKeyword("keyword");

			// Then
			assertFalse(result.isEmpty());
		}

		@Test
		@DisplayName("[실패] 조회한 채용공고 키워드 리스트가 존재하지 않아 예외를 발생시킨다.")
		void shouldThrowExceptionWhenEmploymentKeywordListNotFound() {
			// Given
			given(employmentRepository.searchEmploymentItemsOrderByKeywordFrequency("keyword")).willReturn(
				Optional.empty());

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> employmentService.searchEmploymentsByKeyword("keyword"));
			assertEquals(EmploymentErrorCode.KEYWORD_EMPLOYMENT_LIST_NOT_EXIST, exception.getErrorCode());
		}
	}

	@Nested
	@DisplayName("채용공고 상세 조회")
	class GetEmploymentDetail {

		@Test
		@DisplayName("[성공] 채용공고 상세정보를 조회한다.")
		void shouldGetEmploymentDetailSuccessfully() {
			// Given
			EmploymentDetailResDto employmentDetailResDto = EmploymentDetailResDto.builder().build();
			given(employmentRepository.findEmploymentDetail(anyLong())).willReturn(Optional.of(employmentDetailResDto));

			// When
			EmploymentDetailResDto result = employmentService.readEmploymentDetail(1L);

			// Then
			assertNotNull(result);
		}

		@Test
		@DisplayName("[실패] 존재하지 않는 채용공고로 인해 예외를 발생시킨다.")
		void shouldThrowExceptionWhenEmploymentNotFound() {
			// Given
			given(employmentRepository.findEmploymentDetail(anyLong())).willReturn(Optional.empty());

			// When & Then
			AppException exception = assertThrows(AppException.class, () -> employmentService.readEmploymentDetail(1L));
			assertEquals(EmploymentErrorCode.EMPLOYMENT_NOT_EXIST, exception.getErrorCode());
		}
	}

	@Nested
	@DisplayName("채용공고 회사 유효성 검사")
	class ValidateEmploymentAndCompanyTest {

		@Test
		@DisplayName("[성공] 채용공고가 존재하며, 사용자의 회사와 채용공고 회사가 동일한 경우 유효성 검사에 성공한다.")
		void shouldNotThrowExceptionWhenCompaniesAreEqual() {
			// Given
			given(employmentRepository.findById(anyLong())).willReturn(Optional.of(mockEmployment));

			// When & Then
			assertDoesNotThrow(() -> employmentService.checkEmploymentAndCompanyIsValidate(1L, mockCompanyUser));
		}

		@Test
		@DisplayName("[실패] 채용공고 미존재로 인해 예외를 발생시킨다.")
		void shouldThrowExceptionWhenEmploymentNotExists() {
			// Given
			given(employmentRepository.findById(anyLong())).willReturn(Optional.empty());

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> employmentService.checkEmploymentAndCompanyIsValidate(1L, mockCompanyUser));
			assertEquals(EmploymentErrorCode.EMPLOYMENT_NOT_EXIST, exception.getErrorCode());
		}

		@Test
		@DisplayName("[실패] 채용공고의 회사와 현재 사용자의 회사가 달라 예외를 발생시킨다.")
		void shouldThrowExceptionWhenCompaniesAreNotEqual() {
			// Given
			Company anotherCompany = MockEntityFactory.createMockCompany();
			anotherCompany.updateName("Naver");
			mockCompanyUser.updateUserTypeToCompany(anotherCompany);

			given(employmentRepository.findById(anyLong())).willReturn(Optional.of(mockEmployment));

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> employmentService.checkEmploymentAndCompanyIsValidate(1L, mockCompanyUser));
			assertEquals(EmploymentErrorCode.INVALID_ACCESS_TO_EMPLOYMENT, exception.getErrorCode());
		}
	}

	@Nested
	@DisplayName("채용공고 지원 유효성 검사")
	class ValidateEmploymentAndApplyTest {

		@Test
		@DisplayName("[성공] 채용공고가 존재하며, 지원 기간인 경우 유효성 검사에 성공한다.")
		void shouldNotThrowExceptionWhenEmploymentDateIsValidate() {
			// Given
			given(employmentRepository.findById(anyLong())).willReturn(Optional.of(mockEmployment));

			// When & Then
			assertDoesNotThrow(() -> employmentService.checkEmploymentIsValidate(1L));
		}

		@Test
		@DisplayName("[실패] 현재 날짜가 채용공고의 지원기간이 아니라 예외를 발생시킨다.")
		void shouldThrowExceptionWhenCompaniesAreNotEqual() {
			// Given
			mockEmployment.updateDate(LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(2));
			given(employmentRepository.findById(anyLong())).willReturn(Optional.of(mockEmployment));

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> employmentService.checkEmploymentIsValidate(1L));
			assertEquals(EmploymentErrorCode.INVALID_EMPLOYMENT_APPLY_PERIOD, exception.getErrorCode());
		}
	}
}
