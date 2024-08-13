package com.wanted.recruitment.Apply;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wanted.recruitment.Apply.domain.entity.Apply;
import com.wanted.recruitment.Apply.domain.repository.ApplyRepository;
import com.wanted.recruitment.Apply.error.ApplyErrorCode;
import com.wanted.recruitment.Apply.service.ApplyService;
import com.wanted.recruitment.Employment.domain.entity.Employment;
import com.wanted.recruitment.Employment.service.EmploymentService;
import com.wanted.recruitment.User.domain.entity.User;
import com.wanted.recruitment.User.service.UserService;
import com.wanted.recruitment.global.error.exception.AppException;
import com.wanted.recruitment.setup.MockEntityFactory;

@ExtendWith(MockitoExtension.class)
class ApplyServiceTest {

	@Mock
	private ApplyRepository applyRepository;

	@Mock
	private UserService userService;

	@Mock
	private EmploymentService employmentService;

	@InjectMocks
	private ApplyService applyService;

	private User mockUser;
	private Employment mockEmployment;
	private Apply mockApply;

	@BeforeEach
	void setUp() {
		mockUser = MockEntityFactory.createMockUser();
		mockEmployment = MockEntityFactory.createMockEmployment();
		mockApply = MockEntityFactory.createMockApply();
	}

	@Nested
	@DisplayName("채용공고 지원 정보 저장")
	class SaveApply {

		@Test
		@DisplayName("[성공] 채용공고에 지원한다.")
		void shouldSaveApplySuccessfully() {
			// Given
			given(userService.checkUserIsValidate(anyLong())).willReturn(mockUser);
			given(employmentService.checkEmploymentIsValidate(anyLong())).willReturn(mockEmployment);
			given(applyRepository.save(any(Apply.class))).willReturn(mockApply);

			// When & Then
			assertDoesNotThrow(() -> applyService.saveApply(1L, 1L));
			verify(applyRepository).save(any(Apply.class));
		}

		@Test
		@DisplayName("[실패] 이미 존재하는 지원 이력으로 인해 예외를 발생시킨다.")
		void shouldThrowExceptionWhenDuplicateEmploymentExists() {
			// Given
			given(applyRepository.existsByUserIdAndEmploymentId(any(), any())).willReturn(true);

			// When & Then
			AppException exception = assertThrows(AppException.class,
				() -> applyService.saveApply(1L, 1L));
			assertEquals(ApplyErrorCode.CAN_NOT_APPLY_TO_SAME_EMPLOYMENT.getMessage(), exception.getMessage());
		}
	}

}