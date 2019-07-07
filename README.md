## 카카오페이 서버개발 사전과제

> 주택 금융 서비스 API 개발


### 개요

국내 주택금융 신용보증 기관으로부터 년도별 각 금융기관(은행)에서 신용보증한 금액에 대한
데이터가 주어집니다. 이를 기반으로 아래 기능명세에 대한 API 를 개발하고 각 기능별 Unit
Test 코드를 개발하세요

### Prerequisites

- Java 1.8 +
- Spring Boot 2.1.4 +
- JPA
- Lombok
- Gradle

#### Run Development Server
```
gradle bootRun
or
./gradlew bootRun
```

### API Specifications

#### 회원 가입 / signup 

POST /account/signup

* Request

```json
{
    "username" : "test",
    "password" : "1234"
}
```

* Response

```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjI1MDQxNTYsInVzZXJuYW1lIjoidGVzdCJ9.Fp9k9MGai41mle6HY8lB5CpAOd2FCEfwQyp_lPHqbMw",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjI1MDQzOTYsInVzZXJuYW1lIjoidGVzdCJ9.YmFKlVD5oS_-i1ntoOKWTzBznoQIMd001qQVF0NstJk",
    "token_type": "bearer"
}
```

* Error

| 에러 코드       | 설명                                                         |
| :-----  | ------------------------------------------------------------ |
| 400  | Request 필드 부족      |
| 401 | Username 중복 |

#### 로그인 / signin 

POST /account/singin

* Request

```json
{
    "username" : "test",
    "password" : "1234"
}
```

* Response

```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjI1MDQxNTYsInVzZXJuYW1lIjoidGVzdCJ9.Fp9k9MGai41mle6HY8lB5CpAOd2FCEfwQyp_lPHqbMw",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjI1MDQzOTYsInVzZXJuYW1lIjoidGVzdCJ9.YmFKlVD5oS_-i1ntoOKWTzBznoQIMd001qQVF0NstJk",
    "token_type": "bearer"
}
```

* Error

| 에러 코드       | 설명                                                         |
| :-----  | ------------------------------------------------------------ |
| 400  | Request 필드 부족      |
| 401 | Username or Password 틀릴 경우 |

#### 토큰 재발급 / refresh 

GET /account/refresh

* RequestHeader

| Key      | Value  | 설명                 |
| -------- | ------ | -------------------- |
| Authorization | bearer {refresh_token}   | 로그인시 발급받은 refresh_token |

* Response

```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjI1MDQxNTYsInVzZXJuYW1lIjoidGVzdCJ9.Fp9k9MGai41mle6HY8lB5CpAOd2FCEfwQyp_lPHqbMw",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjI1MDQzOTYsInVzZXJuYW1lIjoidGVzdCJ9.YmFKlVD5oS_-i1ntoOKWTzBznoQIMd001qQVF0NstJk",
    "token_type": "bearer"
}
```

| 필드명 | 응답값                               | 설명                                                         |
| :----- | ------------------------------------ | ------------------------------------------------------------ |
| access_token   | Token                            | access_token 발급      |
| refresh_token | Token | access_token 만료시 사용가능한 refresh_token 발급 |
| token_type | String | Token 타입 명시|

* Error

| 에러 코드       | 설명                                                         |
| :-----  | ------------------------------------------------------------ |
| 401 | InvalidToken 일 경우 (기간만료 / 시그니처 값 불일치 등) |

#### 은행 목록 출력 / getBanks 

GET /apis/banks

* RequestHeader

| Key      | Value  | 설명                 |
| -------- | ------ | -------------------- |
| Authorization | bearer {access_token}   | 로그인시 발급받은 access_token |

* Response

```json
{
    "bank": [
        {
            "name": "주택도시기금"
        },
        {
            "name": "국민은행"
        },
 
        ...
        
        {
            "name": "기타은행"
        }
    ]
}
```

* Error

| 에러 코드       | 설명                                                         |
| :-----  | ------------------------------------------------------------ |
| 401 | InvalidToken 일 경우 (기간만료 / 시그니처 값 불일치 등) |

#### 년도별 각 금융기관 현황 출력 / getFinances

GET /apis/finances

* RequestHeader

| Key      | Value  | 설명                 |
| -------- | ------ | -------------------- |
| Authorization | bearer {access_token}   | 로그인시 발급받은 access_token |

* Response

```json
{
    "name": "주택금융 공급현황",
    "status": [
        {
            "year": "2005 년",
            "total_amount": 48016,
            "detail_amount": {
                "농협은행/수협은행": 1486,
                "하나은행": 3122,
                "우리은행": 2303,
                "국민은행": 13231,
                "신한은행": 1815,
                "외환은행": 1732,
                "주택도시기금": 22247,
                "기타은행": 1376,
                "한국시티은행": 704
            }
        },
        {
            "year": "2006 년",
            "total_amount": 41210,
            "detail_amount": {
                "농협은행/수협은행": 2299,
                "하나은행": 3443,
                "우리은행": 4134,
                "국민은행": 5811,
                "신한은행": 1198,
                "외환은행": 2187,
                "주택도시기금": 20789,
                "기타은행": 1061,
                "한국시티은행": 288
            }
        },
        
        ...
        
        {
            "year": "2017 년",
            "total_amount": 295126,
            "detail_amount": {
                "농협은행/수협은행": 26969,
                "하나은행": 35629,
                "우리은행": 38846,
                "국민은행": 31480,
                "신한은행": 40729,
                "주택도시기금": 85409,
                "외환은행": 0,
                "기타은행": 36057,
                "한국시티은행": 7
            }
        }
    ]
}
```

* Error

| 에러 코드       | 설명                                                         |
| :-----  | ------------------------------------------------------------ |
| 401 | InvalidToken 일 경우 (기간만료 / 시그니처 값 불일치 등) |

#### 년도별 가장 큰 지원금액의 기관명 출력 / getHighestYearBank

GET /apis/finances/highest/year

* RequestHeader

| Key      | Value  | 설명                 |
| -------- | ------ | -------------------- |
| Authorization | bearer {access_token}   | 로그인시 발급받은 access_token |

* Response

```json
{
    "bank": "주택도시기금",
    "year": "2014"
}
```

* Error

| 에러 코드       | 설명                                                         |
| :-----  | ------------------------------------------------------------ |
| 401 | InvalidToken 일 경우 (기간만료 / 시그니처 값 불일치 등) |

#### 특정 은행의 연도별 최대 최소 평균 금액 출력 / getMinMaxYearBankByName

GET /apis/finances/minmax/year?name={bankName}

* RequestHeader

| Key      | Value  | 설명                 |
| -------- | ------ | -------------------- |
| Authorization | bearer {access_token}   | 로그인시 발급받은 access_token |

* RequestParams

| Key      | Value  | 설명                 |
| -------- | ------ | -------------------- |
| name | 주택도시기금   | 조회할 은행 이름 |

* Response

```json
{
    "bank": "주택도시기금",
    "support_amount": [
        {
            "amount": 1732,
            "year": "2006"
        },
        {
            "amount": 8015,
            "year": "2014"
        }
    ]
}
```

* Error

| 에러 코드       | 설명                                                         |
| :-----  | ------------------------------------------------------------ |
| 400 | 존재하지 않은 은행 이름일 경우 |
| 401 | InvalidToken 일 경우 (기간만료 / 시그니처 값 불일치 등) |

#### 특정 은행의 2018년도 특정 달의 지원금액 예측 / getPredictAmountByMonth

GET /apis/finances/predict?name={bankName}&month={month}

* RequestHeader

| Key      | Value  | 설명                 |
| -------- | ------ | -------------------- |
| Authorization | bearer {access_token}   | 로그인시 발급받은 access_token |

* RequestParams

| Key      | Value  | 설명                 |
| -------- | ------ | -------------------- |
| name | 주택도시기금   | 예측할 은행 이름 |
| month | 1   | 예측할 달 |

* Response

```json
{
    "bank": "주택도시기금",
    "amount": 6258,
    "month": 1,
    "year": 2018
}
```

* Error

| 에러 코드       | 설명                                                         |
| :-----  | ------------------------------------------------------------ |
| 400 | 존재하지 않은 은행 이름일 경우 / 입력한 달이 존재하지 않을 시|
| 401 | InvalidToken 일 경우 (기간만료 / 시그니처 값 불일치 등) |


### Entity
#### bank
- 은행의 기본 정보를 가지고 있음
```
bank_id : bigint (PK / GeneratedValue)
bank_name : varchar 
```
#### bank_finance
- 은행의 금융 현황을 가지고 있음 / 논리적으로 bank 테이블과 1:n 관계
```
bank_finance_id : bigint (PK / GeneratedValue)
bank_id : bigint (FK)
year : int 
month : int
amount : int
```
#### user
- 은행의 금융 현황을 가지고 있음 / 논리적으로 bank 테이블과 1:n 관계
```
user_id : bigint (PK / GeneratedValue)
user_name : varchar (UK)
user_password : varchar (Password encode)
```

### 요구 사항 및 문제해결 전략

- 공통 해결 전략
  - 스탠다드 언론 프로그램 개발 목적 
  - 다른 플랫폼(ex. MySQL) 에 종속 되지 않도록 구현한다. (ex. 내장형 DB 사용)
 
- [x] CSV -> DB 로 저장하는 API 개발 
  - 어플리케이션이 시작될때 로컬에 저장하고 있는 CSV 파일을 읽고 DB 에 로드한다.
    - 저장하고 있는 CSV 파일이 없다면 에러로그를 찍고 정상 구동에 실패 하도록 한다.
     
- [x] 주택금융 공급 금융기관(은행) 목록을 출력하는 API 를 개발

  - 최상위 Entity 가 각 은행의 목록을 가지고 있다.

- [x] 년도별 각 금융기관의 지원금액 합계를 출력하는 API 를 개발

  - bank_id 와 year 가 row 끼리 묶으면 된다.
  - Comparable 인터페이스를 구현해 연도별 정렬한다.

- [x] 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발

  - Comparable 인터페이스를 구현해 비교한다.
  - Stream 식으로 비교

- [x] 전체 년도(2005~2016)에서 특정은행의 지원금액 평균 중에서 가장 작은 금액과 큰
  금액을 출력하는 API 개발

  - 각 년도별 비교하는 메소드 구현 

- [x] 특정 은행의 특정 달에 대해서 2018 년도 해당 달에 금융지원 금액을 예측하는 API
  개발

  - 정확도가 떨어지더라도 다른 분석 플랫폼에 의존적이지 않도록 구현을 하도록 하였다. 
  - 바로 적용 가능한 [java-timeseries](<https://github.com/signaflo/java-timeseries>) 오픈소스 라이브러리 사용
  - 차후 다른 모델이 사용될 수 있으므로 PredictFinance 인터페이스를 작성 후 Arima 모델을 사용하는 클래스로 구현
  - 향 후 파라메터 조정을 통한 정확도 조정 계획 

- [x] API 인증을 위해 JWT 사용

  - Account API 를 따로 분리 할 수 있도록 패키지화 
  - Resource API 는 Interceptor 를 구현해서 Authorization 헤더 검증 로직 구현
  
  
 


  

  
