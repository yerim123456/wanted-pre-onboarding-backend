package com.wanted.recruitment.Employment.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wanted.recruitment.Employment.dto.req.EmploymentReqDto;
import com.wanted.recruitment.User.domain.entity.Company;
import com.wanted.recruitment.global.common.BaseTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Employment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Employment extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@NotNull
	private String position;

	private Integer compensation;

	private String content;

	private String skill;

	@NotNull
	private LocalDateTime startDate;

	@NotNull
	private LocalDateTime endDate;

	@Builder
	public Employment(@NotNull Company company, @NotNull String position, Integer compensation, String content,
		String skill, LocalDateTime startDate, LocalDateTime endDate) {
		this.company = company;
		this.position = position;
		this.compensation = compensation;
		this.content = content;
		this.skill = skill;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public void updateEmployment(EmploymentReqDto employmentReqDto) {
		this.position = employmentReqDto.getPosition();
		this.compensation = employmentReqDto.getCompensation();
		this.content = employmentReqDto.getContent();
		this.skill = employmentReqDto.getSkill();
		this.startDate = employmentReqDto.getStartDate();
		this.endDate = employmentReqDto.getEndDate();
	}

	public void updateDate(LocalDateTime startDate, LocalDateTime endDate){
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Employment employment))
			return false;

		return Objects.equals(this.id, employment.getId()) &&
			Objects.equals(this.company, employment.getCompany());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, company);
	}
}
