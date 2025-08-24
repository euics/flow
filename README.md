# 파일 확장자 차단 과제

## 1. 기술 스택

- **언어**: Java 17  
- **프레임워크**: Spring Boot 3.4.9  
- **뷰 템플릿 엔진**: Thymeleaf  
- **데이터베이스 연동**: Spring Data JPA  
- **데이터베이스**: MySQL  
- **빌드 도구**: Gradle

---

## 2. 패키지 구조 및 역할
```text
flow.serverassignment
├─ exception
│  ├─ handler/GlobalExceptionHandler      → 전역 예외 처리
│  └─ BadRequestException / ConflictException / NotFoundException / ...
│
├─ extension
│  ├─ controller
│  │  ├─ ExtensionController              → 화면 라우팅 (Thymeleaf)
│  │  ├─ ExtensionApiController           → REST API (/api/extensions/**)
│  │  └─ request.ExtensionApiRequest      → API 요청 DTO (record)
│  ├─ domain.ExtensionEntity              → 확장자 엔티티
│  ├─ repository
│  │  ├─ ExtensionJpaRepository           → Spring Data JPA 인터페이스
│  │  ├─ ExtensionRepository              → 저장소 추상화
│  │  └─ ExtensionRepositoryImpl          → 커스텀 쿼리 구현
│  ├─ service.ExtensionService            → 비즈니스 로직 구현
│  └─ util.ExtensionType                 → 확장자 타입 (FIXED | CUSTOM)
└─ ServerAssignmentApplication            → 애플리케이션 실행 클래스

resources
├─ templates/index.html
├─ static/css/style.css
└─ application.yml
```

---

## 3. 추가 예외처리 및 고려 사항

다음 조건에 해당하는 경우 예외를 발생시킵니다.  
모든 예외는 `BadRequestException (HTTP 400)` 으로 처리됩니다.

| 발생 조건 | 메시지 |
|-----------|--------|
| 커스텀 추가 시, 고정 목록 이름을 사용한 경우 | `'{name}'은(는) 고정 확장자이므로 커스텀으로 추가할 수 없습니다.` |
| 커스텀 추가 시, 이름 길이 20자 초과 | `커스텀 확장자는 20자를 넘길 수 없습니다.` |
| 커스텀 추가 시, 활성 커스텀 개수 200개 초과 | `커스텀 확장자는 최대 200개까지 추가할 수 있습니다.` |
| 고정 추가 시, 고정 목록에 존재하지 않는 이름 | `유효하지 않은 고정 확장자입니다: {name}` |
| 공통 중복 등록 (동일 name + type 이미 존재) | `이미 등록된 확장자입니다.` |

---

## 4. 소프트 딜리트 정책 적용

- 삭제 시 DB에서 물리적으로 제거하지 않고 `deleted = true` 플래그를 설정합니다.  
- 모든 조회 로직에는 `deleted = false` 조건이 자동으로 적용되며, 사용자에게는 활성 상태의 데이터만 노출됩니다.
