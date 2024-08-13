# 2024 Wanted Pre-onboarding Backend

## 📚 사용 버전
> **Java**: Oracle JDK 17 (version "17.0.11")
> 
> **Spring Boot**: 3.3.2
> 
> **IntelliJ IDEA**: 2023-12 (4.30.0)
> 
### Spring Boot 의존성 버전
> **Tomcat**: 10.1.26
> 
> **MySQL Connector**: 8.3.0
> 
> **Hibernate**: 6.5.2.Final
> 
> **Jackson**: 2.17.2

## 🔧 구현한 API
- [ ] **🔹 채용 공고 등록**
  
- [ ] **🔹 채용 공고 수정**
  - 회사 ID를 제외하고 수정 가능
    
- [ ] **🔹 채용 공고 삭제**
  
- [ ] **🔹 채용 공고 목록 조회**
  - 최신순 정렬
    
- [ ] **🔹 채용 공고 검색**
  - 검색 키워드가 공고 제목, 포지션, 기술에 사용된 빈도 순 > 최신순 정렬
    
- [ ] **🔹 채용 상세 페이지 조회**
  - 채용 내용 및 해당 회사가 올린 다른 채용 공고 포함
    
- [ ] **🔹 채용 공고 지원**
  - 사용자는 1회만 지원 가능
  - 지원 기간에만 지원 가능
  
## 🚀 서버 구동 방법
1. `recruitment` 데이터베이스 생성
2. `application-local.properties` 파일 생성 후 환경에 맞춰 다음 속성 정의:
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
3. 서버 실행

## 📝 API 명세서 확인 방법
1. 서버 구동
2. [Swagger UI](http://localhost:8080/swagger-ui/index.html)에서 API 명세서 확인

## 🗺️ ERD (Entity Relationship Diagram)
- **User - Company**  
  한 회사에 여러 명의 관리자가 계정을 가질 수 있도록 `User`와 `Company`는 **ManyToOne** 관계로 설정

- **Company - Employment**  
  한 회사에서 다양한 채용 공고를 낼 수 있기에 `Company`와 `Employment`는 **ManyToOne** 관계로 설정. 
  `Employment` 엔티티는 지원 기간에 따른 중복 검증이 가능하도록 구현

- **User, Employment - Apply**  
  여러 사용자가 여러 채용 공고에 지원할 수 있기에 `Apply`라는 테이블을 생성하여 각각 **ManyToOne** 관계로 설정

![ERD Diagram](https://github.com/user-attachments/assets/81f86e50-ea3b-4543-8292-137b2de937ff)

## 👍 배운 점
- 현재 서비스에서 다른 서비스를 의존하는 형태로 구현하였습니다. 이는 해당 도메인 관련 로직을 해당 도메인에서 다룰 수 있게 하여, 코드의 가독성과 유지보수성을 높인다고 판단했습니다.
- 유효성 검증 로직을 서비스 단에 배치하고, 다른 서비스에서 이를 불러와 검증하는 방식으로 구현했습니다. 하지만 이러한 구조는 서비스가 방대해질 경우 **순환 참조**의 문제가 발생할 수 있다는 점을 알게 되었습니다.
- 현재 구현에서는 문제가 없지만, 다른 프로젝트에서는 이러한 문제를 방지하기 위해 서비스 레이어를 분리하는 방식으로 적용해 볼 필요가 있음을 배웠습니다.

---

