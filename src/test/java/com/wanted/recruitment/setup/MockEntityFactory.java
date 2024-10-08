package com.wanted.recruitment.setup;

import java.time.LocalDateTime;

import com.wanted.recruitment.Apply.domain.entity.Apply;
import com.wanted.recruitment.Employment.domain.entity.Employment;
import com.wanted.recruitment.User.domain.entity.Company;
import com.wanted.recruitment.User.domain.entity.User;

public class MockEntityFactory {

	// Company
	public static Company createMockCompany() {
		return Company.builder()
			.name("Wanted")
			.country("South Korea")
			.region("Seoul")
			.build();
	}

	// Company Type User
	public static User createMockCompanyUser() {
		User companyUser = User.builder()
			.email("wantedAdmin01@wanted.com")
			.nickname("wantedAdmin01")
			.profileImagePath("https://img-url.com")
			.build();
		companyUser.updateUserTypeToCompany(createMockCompany());
		return companyUser;
	}

	// User Type User
	public static User createMockUser() {
		return User.builder()
			.email("yearim1226@naver.com")
			.nickname("yerim")
			.profileImagePath("https://img-url.com")
			.build();
	}

	// Employment
	public static Employment createMockEmployment() {
		return Employment.builder()
			.company(createMockCompany())
			.position("Backend Developer")
			.content("Job Content")
			.skill("Java")
			.startDate(LocalDateTime.now())
			.endDate(LocalDateTime.now().plusDays(10))
			.build();
	}

	// Apply
	public static Apply createMockApply() {
		return Apply.builder()
			.user(createMockUser())
			.employment(createMockEmployment())
			.build();
	}
}
