package com.wanted.recruitment.User.domain.entity;

import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wanted.recruitment.global.common.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Company")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Company extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	private String country;

	private String region;

	@Builder
	public Company(String name, String country, String region) {
		this.name = name;
		this.country = country;
		this.region = region;
	}

	public void updateName(String name){
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Company company))
			return false;

		return Objects.equals(this.id, company.getId()) &&
			Objects.equals(this.name, company.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
