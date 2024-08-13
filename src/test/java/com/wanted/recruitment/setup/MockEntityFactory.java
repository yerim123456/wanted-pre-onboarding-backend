package com.wanted.recruitment.setup;

import com.wanted.recruitment.User.domain.entity.Company;
import com.wanted.recruitment.User.domain.entity.User;

public class MockEntityFactory {

	// Company
	public static Company createMockCompany(String companyName) {
		return Company.builder()
			.name(companyName)
			.country("South Korea")
			.region("Seoul")
			.build();
	}

	// Company Type User
	public static User createMockCompanyUser(String companyName) {
		User companyUser = User.builder()
			.email("wantedAdmin01@wanted.com")
			.nickname("wantedAdmin01")
			.profileImagePath("https://img-url.com")
			.build();
		companyUser.updateUserTypeToCompany(createMockCompany(companyName));
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
}
