# Reactive 과제

## 프로젝트 구조

| 모듈명   | 설명                    |
|-------|-----------------------|
| root  | SpringBoot Main 클래스   |
| user  | 사용자 하위 도메인            |
| item  | 상품 하위 도메인             |
| query | 클라이언트에서 필요한 복잡한 쿼리 조회 |

* 테스트 코드는 root 모듈에 있습니다 (`/src/test/kotlin`)

## API 리스트

### 사용자 (User)

| 항목     | API             |
|--------|-------------------|
| 리스트 조회 | GET /users        | 
| 회원 가입  | POST /users       | 
| 로그인    | POST /users/login |

### 상품 (Item)

| 항목     | API              |
|--------|--------------------|
| 리스트 조회 | GET /items         | 
| 상품 등록  | POST /items/{id}   | 
| 상품 수정  | PUT /items/{id}    |
| 상품 삭제  | DELETE /items/{id} |

## 프로젝트 설명

Command(Create, Update, Delete)는 하위 도메인 모듈(user, item)에서 되도록 담당하게 헀습니다
또한 간단한 조회 또한 하위 도메인 모듈에게 위임했습니다 (하나의 테이블 조회)

Query(Read), 테이블을 2개 이상 조회하는 쿼리의 경우 query 모듈에 작성하였습니다

모듈러 모놀리스를 적용을 위해 Gradle 멀티 모듈을 도입하였습니다

> 모듈러 모놀리스를 적용한 이유는 MSA처럼 크지 않고 
> 모놀리식처럼 하위 도메인간 결합이 강하지 않기 때문에 토이로 하기 좋은 프로젝트라 생각했습니다

## 기술 스펙

- Java 11
- WebFlux
- PostgresSQL
- JWT 인증
- 코루틴
- 멀티 모듈 (Gradle)

## 적용한 것 

> 소스 코드 레벨에서 적용한 것

- 테스트 (통합, 단위)
- 예외 핸들링 (`ErrorWebExceptionHandler`)
- 인증 필터 (`WebFilter`)


## 하지 못한 것

- WebFlux와 Hibernate(JPA) 적용
    - EntityManager 등록 등, JDBC 레벨에서의 이슈를 해결하지 못하였습니다

- WebFlux 스러운 코드 컨벤션 적용
    - WebFlux에 대한 충분히 학습하지 못하였습니다
 
- 조금 더 복잡한 도메인 구축
  - 여러 상황에서의 해결 능력을 보여줄 수 있는 도메인을 구축하지 못하였습니다