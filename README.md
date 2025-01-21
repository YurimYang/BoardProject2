### 🌱 목표

**몽고DB를 이용한 게시판 API 만들기 : 최소MVP - 등록/수정/삭제/조회**

- 기술스택 : Java17 이상, SpringBoot 3.x이상, 몽고DB 4.x 를 활용한 개발환경 구성
- 게시판 MVP에 해당하는 RespAPI를 만들어보세요. - CRUD & Paging
- Swagger 을 이용해서 API확인 할 수 있도록 준비

<br></br>

### 🛠️ 기술스택
- Java 17 
- Spring Boot 3.3.1 
- MongoDB 7.0.16 
- Docs - Swagger 2.0.2 
- Test - JUnit

<br></br>

## 🧩 테이블 구조

#### Board
![Image](https://github.com/user-attachments/assets/229904b0-b380-4da3-9209-42fa8c6e619a)
#### Comment
![Image](https://github.com/user-attachments/assets/8ef64231-a23c-4ad6-a7ca-68efd7624edc)
#### Base Entity
![Image](https://github.com/user-attachments/assets/b90ca9c0-0612-45c3-9796-bd2a7d6ead5b)


<br></br>

## 🏛️ 아키텍처 설계

#### [Domain 형 구조 & 인터페이스/구현체 분리]

**Domain형 구조**

각 도메인 별로 패키지를 분리하여 도메인 별 관리를 직관적으로 표현했습니다.

추후 기획이 추가되더라도, 코드의 재활용이 가능합니다.

**인터페이스/구현체 분리**

객체지향적 관점에서 구현체에 의존하지 않고, 추상화된 인터페이스에 의존하게 구현했습니다.

기능이 확장되고, 요구사항이 변해도 변화하는 모듈에만 수정하면 되므로 코드의 재사용성을 늘릴 수 있습니다.

<br></br>

## ☎️ RESTful API

http://127.0.0.1:8080/swagger-ui/index.html#/BOARD API (로컬에서만 진행가능)

![Image](https://github.com/user-attachments/assets/1d179130-fe64-4fd7-8484-3b855e6a22ef)

<br></br>

## 🧪 Test Code

- JUnit을 활용한 Service 중심의 Unit Test 진행 `@ExtendWith(MocktioExtension.class)`

    - `@InjectMocks` : 모의 클래스의 의존성을 주입받음

    - `@Mock` : 모의 클래스 생성


  → Service Layer가 데이터베이스 작업을 위해 DAO에 의존

- given-when-then 구조로 테스트코드 작성

<br></br>

## 📣 Issue

### [RELEASE-1] mongoRepository와 mongoTemplate에 대한 차이점 파악

- mongoRepository (https://github.com/YurimYang/BoardProject ) : 모든 필드를 포함하는 POJO(객체) 와 함께 작동하는 기본적인 CRUD만 제공

- mongoTemplate (https://github.com/YurimYang/BoardProject2 ) : 관련 필드 대상으로 연산자를 사용하여 DB 수정

<br></br>

### [RELEASE-2] mongoRepository와 mongoTemplate 연동 
mongoRepository와 mongoTemplate를 연동하여, 복잡한 쿼리의 경우 mongoTemplate의 연산자를 사용할 수 있도록 구현

  <img width="1098" alt="Image" src="https://github.com/user-attachments/assets/c78e3479-b2f0-4b62-bf04-7874ca25a07c" />

<br></br>

### id 설정 - ObjectId, 자동증가 Id, UUID

<img width="714" alt="Image" src="https://github.com/user-attachments/assets/5ea2b4e8-6281-4d8c-b71a-21dfe8104016" />

<br></br>

### mongoDB와 JPA의 차이점

**JPA**

영속성 컨텍스트에서 관리되는 Entity에 대해 dirty checking을 진행하여 자동으로 변경을 감지할 수 있습니다. 그렇기에 DB에 적용하는 쿼리가 아닌, Entity 내부 별도 메소드를 호출하는 것만으로도 변경 내용을 저장할 수 있습니다.

**mongoDB**

JPA와 다리 영속성 컨텍스트가 없으므로 변경된 Entity를 수동으로 저장해야합니다. 그렇기에, DAO에 쿼리를 직접 작성하여 데이터베이스에 반영해야합니다.

<br></br>

### 예외처리

* ApplicationException을 중심으로 커스텀 예외를 통합

    - RuntimeException을 상속하여 비검사 예외를 사용해 코드를 간결하게 작성 가능 (컴파일러가 처리 여부를 강제하지 않으며, 필요시에만 try-catch 로 처리)
    
    - 개발자가 특정한 상황에서 발생하는 예외를 명확하게 정의 가능
    
    - 대부분의 비즈니스 로직에서 발생하는 예외는 런타임 예외로 간주됨
<br></br>
* @RestControllerAdvice를 사용하여 글로벌 예외처리를 제공 
  * ExceptionHandler를 통해 ApplicationException을 캐치하고, 클라이언트에게 반환