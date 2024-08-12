package com.wanted.recruitment.User.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wanted.recruitment.User.domain.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
