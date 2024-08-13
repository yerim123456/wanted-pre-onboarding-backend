package com.wanted.recruitment.Apply.domain.entity;

import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wanted.recruitment.Employment.domain.entity.Employment;
import com.wanted.recruitment.User.domain.entity.User;
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
@Table(name = "Apply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Apply extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "employment_id")
	private Employment employment;

	@Builder
	public Apply(@NotNull User user, @NotNull Employment employment) {
		this.user = user;
		this.employment = employment;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Apply apply))
			return false;

		return Objects.equals(this.id, apply.getId()) &&
			Objects.equals(this.user, apply.getUser()) &&
			Objects.equals(this.employment, apply.getEmployment());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, user, employment);
	}
}
