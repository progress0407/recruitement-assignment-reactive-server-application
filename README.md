# Reactive 과제

## 프로젝트 구조

| 항목    | 설명                    |
|-------|-----------------------|
| root  | SpringBoot Main 클래스   |
| user  | 사용자 하위 도메인            |
| item  | 상품 하위 도메인             |
| query | 클라이언트에서 필요한 복잡한 쿼리 조회 |

* 테스트 코드는 root 모듈에 있습니다 (`/src/test/kotlin`)

## API

- User
  - GET (리스트 조회)
  - POST
  - 로그인

- Item
  - GET (리스트 조회)
  - POST
  - PUT
  - DELETE

## 프로젝트 설명

Command(Create, Update, Delete)의 경우하위 도메인 모듈(user, item)에서 가급적 처리하고자 헀습니다
또한 간단한 조회 또한 하위 도메인 모듈에게 위임했습니다 (하나의 테이블 조회)

Query(Read), 그 중에서도 복잡한 쿼리의 경우 query 모듈에 작성하였습니다  
성능 튜일이 필요할 경우 해당 모듈만 확인하면 될 것으로 보입니다

멀티모듈을 도입한 이유는 MSA 처럼 데이터 정합성으로부터 자유롭지만 각 하위 도메인간의 결합도를 느슨하게 가져갈 수 있을 것이라 생각하여 적용해보았습니다  
단, 크지 않은 프로젝트의 경우 단일 모듈로 적용하는 것이 좋을 것이라 생각했습니다


## 적용한 것

- Java 11
- WebFlux
- PostgresSQL
- 멀티 모듈
- 테스트 (통합, 단위)

## 하지 못한 것

- WebFlux와 Hibernate(JPA) 적용
  - EntityManager 등록 등, JDBC 레벨에서의 이슈를 해결하지 못하였습니다.

- WebFlux 스러운 코드 컨벤션 적용
  - WebFlux에 대한 충분히 학습하지 못하였습니다
 
