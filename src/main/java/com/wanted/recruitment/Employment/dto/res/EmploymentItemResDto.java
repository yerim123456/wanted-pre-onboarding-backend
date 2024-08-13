package com.wanted.recruitment.Employment.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmploymentItemResDto {
	private Long employmentId;
	private String companyName;
	private String country;
	private String region;
	private String position;
	private Integer compensation;
	private String skill;

	public EmploymentItemResDto(Long employmentId, String companyName, String country, String region, String position,
		Integer compensation, String skill) {
		this.employmentId = employmentId;
		this.companyName = companyName;
		this.country = country;
		this.region = region;
		this.position = position;
		this.compensation = compensation;
		this.skill = skill;
	}
}