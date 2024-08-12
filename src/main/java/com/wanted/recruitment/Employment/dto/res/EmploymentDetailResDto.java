package com.wanted.recruitment.Employment.dto.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class EmploymentDetailResDto {
	private Long employmentId;
	private String companyName;
	private String country;
	private String region;
	private String position;
	private Integer compensation;
	private String skill;
	private String content;
	private List<Long> otherEmploymentIds;

	public EmploymentDetailResDto(Long employmentId, String companyName, String country, String region, String position,
		Integer compensation, String skill, String content) {
		this.employmentId = employmentId;
		this.companyName = companyName;
		this.country = country;
		this.region = region;
		this.position = position;
		this.compensation = compensation;
		this.skill = skill;
		this.content = content;
	}

	public void updateOtherEmploymentIds(List<Long> otherEmploymentIds) {
		this.otherEmploymentIds = otherEmploymentIds;
	}
}