# 🧠 TechView-BE
**IT 취업 준비생을 위한 기술 면접 학습 & 커뮤니티 백엔드**

TechView-BE는 IT 취업 준비생이 기술 면접 질문을 학습하고, 면접 후기와 피드백을 공유할 수 있도록 지원하는 REST API 서버입니다. 인증·권한 관리부터 질문 뱅크, 게시판, 마이페이지, 관리자 기능까지 포함해 프론트엔드와 바로 연동 가능한 완성도 높은 백엔드 구조를 갖추고 있습니다.

---

## 🔍 한눈에 보기
- **역할**: TechView 서비스의 백엔드 API 서버
- **대상 사용자**: IT 취업 준비생 & 관리자
- **핵심 기능**
  - JWT 기반 인증 / 세션 관리
  - 기술 면접 질문 뱅크 & 추천
  - 면접 후기 커뮤니티 (게시글·댓글·북마크)
  - 사용자 개인화 (마이페이지)
  - 관리자용 카테고리·스킬 관리
- **아키텍처**: Spring Boot 기반 REST API + Layered Architecture

---

## 🏗️ 기술 스택
### ☕ Backend
- Java 17 (LTS)
- Spring Boot (Gradle Plugin 4.0.0)
- Spring Web / Spring Data JPA
- Spring Security
- Spring Validation

### 🗄️ Database & Query
- MySQL
- QueryDSL (동적 쿼리)
- JPA Auditing (생성/수정 시각 자동 기록)

### 🔐 Auth & Docs
- JWT (JJWT)
- Swagger / OpenAPI (springdoc-openapi)
- Lombok

### 🧪 Dev & Test
- Gradle (Kotlin DSL)
- JUnit
- Spring Devtools

---

## 🧩 아키텍처 & 공통 인프라
### 📦 레이어드 구조
- Controller → Service → Repository → Domain
- `/api` 프리픽스를 모든 컨트롤러에 강제
- 공통 응답 포맷(`ApiResponse`) 사용
- 전역 예외 처리(`GlobalExceptionHandler`)로 일관된 에러 응답 제공

### 🔍 데이터 감사 (Auditing)
- `@EnableJpaAuditing` 활성화
- 엔티티에 `@CreatedDate`, `@LastModifiedDate` 적용
- 게시글·댓글 등 주요 데이터의 생성/수정 시점 자동 관리

### 🔐 보안 파이프라인
- JWT 기반 인증으로 일부 GET API만 공개, 나머지는 인증 필수
- `JwtAuthenticationFilter` + `JwtTokenProvider`가 토큰 검증 및 컨텍스트 주입
- Refresh Token은 HttpOnly + Secure Cookie로 전달하고 Rotate-on-Refresh(RTR) 전략 적용

### 📘 API 문서화
- springdoc-openapi로 Swagger UI 자동 생성
- `@AuthApi`, `@PublicApi` 메타 어노테이션을 이용해 인증 필요 여부와 보안 스펙을 문서에 그대로 반영

### ⚠️ 검증 & 에러 처리
- Bean Validation + 한국어 메시지를 적용한 사용자 친화적 검증 피드백
- `ErrorCode` 기반 커스텀 예외 설계로 의미 있는 오류 응답 제공

---

## 🚀 핵심 기능
1. **인증 / 세션 관리**
   - 회원가입 / 로그인 / 로그아웃
   - 비밀번호 변경 및 임시 비밀번호 발급 (메일 발송 로직 TODO)
   - AccessToken / RefreshToken 발급 및 재발급 (RTR 전략)
2. **사용자 개인화 (마이페이지)**
   - 이름, 관심 분야, 기술 스택 수정/조회
   - User ↔ Category / Skill N:M 관계로 사용자 맞춤 데이터 제공
3. **기술 면접 질문 뱅크**
   - 질문 CRUD
   - 카테고리·스킬 기반 검색, 난이도 기반 추천
   - QueryDSL을 활용한 조건부 동적 검색
4. **게시글 & 댓글 커뮤니티**
   - 면접 후기 / 질문 게시글 작성 및 조회수 증가
   - 좋아요 / 북마크 기능, 댓글 CRUD
   - 비밀번호 해시 저장과 검증 로직으로 이중 보호
5. **북마크 관리**
   - 게시글 북마크 토글
   - 북마크 목록 조회 및 여부 확인 API
6. **관리자 기능**
   - 카테고리 / 기술 스택 관리
   - MAJOR / MEDIUM / MINOR 계층 구조로 일관된 분류 체계 유지

---

## 🔄 API 응답 & 규약
- 모든 API는 `ApiResponse<T>` 형식으로 응답하여 성공/실패 구조를 통일
- `ErrorCode` 기반 예외 처리로 상태 코드와 메시지를 명확히 전달
- Swagger 문서와 실제 API 스펙을 동기화해 프론트엔드와의 계약을 보장

---

## 🛠️ 개발 과정 요약
1. Gradle + Spring Boot 기반 초기 세팅
2. User, Question, Post 등 핵심 도메인 모델링
3. 서비스 계층에 비즈니스 로직 캡슐화
4. JWT 기반 인증·보안 파이프라인 구축
5. 전역 예외 처리 & 검증 메시지 로컬라이징
6. Swagger 기반 API 계약 정리

---

## ▶️ 실행 방법
```bash
# 프로젝트 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

---

## ⚙️ 환경 설정
- MySQL 연결 정보는 `application.yml` 또는 `application.properties`에 설정합니다.
- JWT 시크릿 키 및 만료 시간은 환경 변수 또는 외부 설정으로 관리하는 것을 권장합니다.
