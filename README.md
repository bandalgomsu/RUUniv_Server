## RUUniv (대학생 인증 Third Party API)

---

![re1](https://github.com/user-attachments/assets/55364e4f-f50d-4756-8171-b54b9e25bb1b)

### 링크

- 🔥[Site](http://ruuniv.xyz)

### 개발기간

- (2024 / 06 / 22 ~ 배포중)

### 기여도 (개인 프로젝트)

- BE : 100%
- FE : 100%

### 주요 기능

- 이메일을 통한 대학생 인증

---

## 기술 스택

- Kotlin , Spring WebFlux , Coroutine , R2DBC
- MySql , Redis
- React , JS
- EC2 , Cloud Watch

---

## Cording Standard

### Package Structure

- app
    - 각각의 도메인 패키지 및 외부 기술에 대한 인터페이스가 (Adapter) 존재하고 각 도메인 패키지는 Controller , Service , Implement , Dao 패키지로 구성될 수
      있음
- common
    - 공통 예외 처리 등 공통된 내부 기술 관리
- Infrastructure
    - 외부 기술 관리 (DB , Redis , Http Client 등등)

### Layer

- Controller
    - Api 엔드포인트 역할
    - 네이밍 : Domain + 비즈니스 로직 + Controller
        - StudentVerificationController
        - StudentQueryController
- Service
    - Implement의 조합으로 비즈니스 로직 담당
    - 네이밍 : Domain + 비즈니스 로직 + Controller
        - StudentVerificationService
        - StudentQueryService
- Implement
    - DataAccess의 조합으로 용도에 맞게 데이터 접근 로직을 매핑하고 엔티티 ↔ 개념객체 변환
    - 네이밍 : Domain + 역할
        - StudentReader
        - StudentFinder
        - StudentWriter
        - StudentProcessor
- DataAccess
    - 데이터의 접근하여 엔티티를 얻는 계층으로 DB를 통해 접근하거나 Http를 통해 접근하는 등의 방법이 있음
    - 네이밍 : Domain + Dao (DB 뿐 아니라 Data 접근을 위한 모든 기술을 의미)
    - 해당 Dao의 구현체는 Infrastructure 에서 구현 (DB, HttpClient 등등을 통해 구햔)

- 계층별 CRUD 네이밍

  |   | Controller,Service | Implement ,  DataAccess |
                |---|--------------------|-------------------------|
  | C | Create             | Add                     |
  | R | Get                | Read , Find             |
  | U | Update             | Update                  |
  | D | Delete             | Delete                  |

### Rule

- Layer 의존성은 아래로 향해야 한다
- 엔티티와 개념객체는 분리해야 한다 (개념객체 = App 패키지 내부에서 사용하는 도메인의 순수 자바 객체)
- App에서 infrastrucure를 직접적으로 의존할 수 없다 (Di를 통해 주입받아야함)
- 단수 , 복수 구분을 확실하게 해야한다
- 도메인 package 이름은 가능하면 복수형으로 해야한다
- uri에서 해당 도메인 부분은 복수형으로 해야한다 (/api/v1/students/…)
- 여기에 더해서 통상적으로 사용하는 자바/코틀린 코딩 스탠다드 준수 (메서드 이름은 동사 시작 , 클래스 이름은 카멜케이스 등등)