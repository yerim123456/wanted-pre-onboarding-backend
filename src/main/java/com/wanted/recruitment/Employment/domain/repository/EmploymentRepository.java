package com.wanted.recruitment.Employment.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wanted.recruitment.Employment.domain.entity.Employment;
import com.wanted.recruitment.Employment.dto.res.EmploymentDetailResDto;
import com.wanted.recruitment.Employment.dto.res.EmploymentItemResDto;
import com.wanted.recruitment.User.domain.entity.Company;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {

	// 해당 회사, 같은 직책 공고이면서 마감일 지나지 않은 채용공고 존재 여부 확인
	@Query("SELECT COUNT(e) > 0 " +
		"FROM Employment e " +
		"WHERE e.company = :company AND e.position = :position " +
		"AND e.endDate > CURRENT_DATE")
	boolean existsByCompanyAndPositionAndActive(@Param("company") Company company, @Param("position") String position);

	// 채용공고 전체 검색
	@Query("SELECT new com.wanted.recruitment.Employment.dto.res.EmploymentItemResDto(" +
		"e.id, c.name, c.country, c.region, e.position, e.compensation, e.skill) " +
		"FROM Employment e " +
		"JOIN e.company c " +
		"ORDER BY e.createdAt DESC")
	Optional<List<EmploymentItemResDto>> findAllEmploymentItemsOrderByCreatedAtDesc();

	// 채용공고 키워드 검색, 키워드 빈출 순으로 정렬
	@Query("SELECT new com.wanted.recruitment.Employment.dto.res.EmploymentItemResDto(" +
		"e.id, c.name, c.country, c.region, e.position, e.compensation, e.skill) " +
		"FROM Employment e " +
		"JOIN e.company c " +
		"WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		"OR LOWER(e.position) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		"OR LOWER(e.skill) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		"ORDER BY " +
		"((LENGTH(LOWER(c.name)) - LENGTH(REPLACE(LOWER(c.name), LOWER(:keyword), ''))) / LENGTH(:keyword) + " +
		"(LENGTH(LOWER(e.position)) - LENGTH(REPLACE(LOWER(e.position), LOWER(:keyword), ''))) / LENGTH(:keyword) + " +
		"(LENGTH(LOWER(e.skill)) - LENGTH(REPLACE(LOWER(e.skill), LOWER(:keyword), ''))) / LENGTH(:keyword)) DESC, " +
		"e.createdAt DESC")
	Optional<List<EmploymentItemResDto>> searchEmploymentItemsOrderByKeywordFrequency(@Param("keyword") String keyword);

	// 채용공고 상세 확인
	@Query("SELECT new com.wanted.recruitment.Employment.dto.res.EmploymentDetailResDto(" +
		"e.id, c.name, c.country, c.region, e.position, e.compensation, e.skill, e.content ) " +
		"FROM Employment e " +
		"JOIN e.company c " +
		"WHERE e.id = :employmentId")
	Optional<EmploymentDetailResDto> findEmploymentDetail(@Param("employmentId") Long employmentId);

	// 같은 회사 다른 공고 찾아보기
	@Query("SELECT em.id " +
		"FROM Employment em " +
		"WHERE em.company.name = :companyName AND em.id != :employmentId")
	List<Long> findOtherEmploymentIds(@Param("companyName") String companyName, @Param("employmentId") Long employmentId);
}
