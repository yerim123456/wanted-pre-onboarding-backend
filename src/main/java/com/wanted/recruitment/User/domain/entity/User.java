package com.wanted.recruitment.User.domain.entity;

import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wanted.recruitment.User.model.UserType;
import com.wanted.recruitment.global.common.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "User")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class User extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String nickname;

	private String profileImagePath;

	@NotNull
	@Enumerated(EnumType.STRING)
	private UserType userType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Builder
	public User(@NotNull String email, String nickname, String profileImagePath) {
		this.email = email;
		this.nickname = nickname;
		this.profileImagePath = profileImagePath;
		this.userType = UserType.USER;
	}

	public void updateUserTypeToUser() {
		this.userType = UserType.USER;
		this.company = null;
	}

	public void updateUserTypeToCompany(Company company) {
		this.userType = UserType.COMPANY;
		this.company = company;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User user))
			return false;

		return Objects.equals(this.id, user.getId()) &&
			Objects.equals(this.email, user.getEmail());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, nickname);
	}
}
