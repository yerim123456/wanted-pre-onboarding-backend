package com.wanted.recruitment.Employment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.recruitment.Employment.domain.entity.Employment;
import com.wanted.recruitment.Employment.domain.repository.EmploymentRepository;
import com.wanted.recruitment.Employment.dto.req.EmploymentReqDto;
import com.wanted.recruitment.Employment.dto.res.EmploymentDetailResDto;
import com.wanted.recruitment.Employment.dto.res.EmploymentItemResDto;
import com.wanted.recruitment.Employment.error.EmploymentErrorCode;
import com.wanted.recruitment.User.domain.entity.User;
import com.wanted.recruitment.User.service.UserService;
import com.wanted.recruitment.global.error.exception.AppException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmploymentService {
	private final EmploymentRepository employmentRepository;
	private final UserService userService;

	// 채용공고 저장
	@Transactional
	public void saveEmployment(Long userId, EmploymentReqDto employmentReqDto) {
		// 회사인 사용자 유효성 체크
		User companyUser = userService.checkCompanyUserIsValidate(userId);

		// 시작 날짜, 끝 날짜 기간 유효성 체크
		if(employmentReqDto.getStartDate().isAfter(employmentReqDto.getEndDate())){
			throw new AppException(EmploymentErrorCode.INVALID_DATE_PERIOD);
		}

		// 채용공고 중복 여부 확인
		if (employmentRepository.existsByCompanyAndPositionAndActive(companyUser.getCompany(),
			employmentReqDto.getPosition())) {
			throw new AppException(EmploymentErrorCode.CAN_NOT_SAVE_SAME_EMPLOYMENT);
		}

		// 채용공고 등록
		employmentRepository.save(Employment.builder()
			.company(companyUser.getCompany())
			.position(employmentReqDto.getPosition())
			.compensation(employmentReqDto.getCompensation())
			.content(employmentReqDto.getContent())
			.skill(employmentReqDto.getSkill())
			.startDate(employmentReqDto.getStartDate())
			.endDate(employmentReqDto.getEndDate())
			.build()
		);
	}

	// 채용공고 수정
	@Transactional
	public void updateEmployment(Long userId, Long employmentId, EmploymentReqDto employmentReqDto) {
		// 회사인 사용자 유효성 체크
		User companyUser = userService.checkCompanyUserIsValidate(userId);

		// 시작 날짜, 끝 날짜 기간 유효성 체크
		if(employmentReqDto.getStartDate().isAfter(employmentReqDto.getEndDate())){
			throw new AppException(EmploymentErrorCode.INVALID_DATE_PERIOD);
		}

		// 채용공고 및 회사 권한 유효성 체크
		Employment employment = checkEmploymentAndCompanyIsValidate(employmentId, companyUser);

		// 값 수정
		employment.updateEmployment(employmentReqDto);

		// 채용공고 업데이트
		employmentRepository.save(employment);
	}

	// 채용공고 삭제
	@Transactional
	public void deleteEmployment(Long userId, Long employmentId) {
		// 회사인 사용자 유효성 체크
		User companyUser = userService.checkCompanyUserIsValidate(userId);

		// 채용공고 및 회사 권한 유효성 체크
		Employment employment = checkEmploymentAndCompanyIsValidate(employmentId, companyUser);

		// 채용공고 삭제
		employmentRepository.delete(employment);
	}

	// 채용공고 전체 검색
	@Transactional(readOnly = true)
	public List<EmploymentItemResDto> readAllEmploymentDesc() {
		return employmentRepository.findAllEmploymentItemsOrderByCreatedAtDesc()
			.orElseThrow(() -> new AppException(EmploymentErrorCode.EMPLOYMENT_LIST_NOT_EXIST));
	}

	// 채용공고 키워드 검색
	@Transactional(readOnly = true)
	public List<EmploymentItemResDto> searchEmploymentsByKeyword(String keyword) {
		return employmentRepository.searchEmploymentItemsOrderByKeywordFrequency(keyword)
			.orElseThrow(() -> new AppException(EmploymentErrorCode.KEYWORD_EMPLOYMENT_LIST_NOT_EXIST));
	}

	// 채용공고 상세 확인
	@Transactional(readOnly = true)
	public EmploymentDetailResDto readEmploymentDetail(Long employmentId) {
		EmploymentDetailResDto employmentDetailResDto = employmentRepository.findEmploymentDetail(employmentId)
			.orElseThrow(() -> new AppException(EmploymentErrorCode.EMPLOYMENT_NOT_EXIST));

		// 해당 회사의 다른 공고 id 리스트 가져와서 수정
		employmentDetailResDto.updateOtherEmploymentIds(
			employmentRepository.findOtherEmploymentIds(employmentDetailResDto.getCompanyName(), employmentId));

		return employmentDetailResDto;
	}

	// 회사 채용공고 유효성 확인
	@Transactional(readOnly = true)
	public Employment checkEmploymentAndCompanyIsValidate(Long employmentId, User companyUser) {
		// 유효한 채용공고인지 확인
		Employment employment = employmentRepository.findById(employmentId)
			.orElseThrow(() -> new AppException(EmploymentErrorCode.EMPLOYMENT_NOT_EXIST));

		// 해당 회사 채용공고인지 확인
		if (!employment.getCompany().equals(companyUser.getCompany())) {
			throw new AppException(EmploymentErrorCode.INVALID_ACCESS_TO_EMPLOYMENT);
		}

		return employment;
	}

	// 채용공고 지원 유효성 확인
	@Transactional(readOnly = true)
	public Employment checkEmploymentIsValidate(Long employmentId){
		// 유효한 채용공고인지 확인
		Employment employment = employmentRepository.findById(employmentId)
			.orElseThrow(() -> new AppException(EmploymentErrorCode.EMPLOYMENT_NOT_EXIST));

		// 현재 날짜가 채용공고의 시작날짜와 마감날짜 사이인지 확인
		LocalDateTime now = LocalDateTime.now();
		if (now.isBefore(employment.getStartDate()) || now.isAfter(employment.getEndDate())) {
			throw new AppException(EmploymentErrorCode.INVALID_EMPLOYMENT_APPLY_PERIOD);
		}

		return employment;
	}

}
