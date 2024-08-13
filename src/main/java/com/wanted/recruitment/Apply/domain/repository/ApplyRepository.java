package com.wanted.recruitment.Apply.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wanted.recruitment.Apply.domain.entity.Apply;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
	boolean existsByUserIdAndEmploymentId(Long userId, Long employmentId);
}
