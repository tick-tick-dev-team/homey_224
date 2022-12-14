# 스프링 연습 프로젝트
인프런 `스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술` 수강하고 연습한 프로젝트


## 환경설정
- jdk 1.8 이상 (강의에선 java 11 사용)
- eclipse (3.8, 4 이상에서 작동 확인) (강의에선 intelliJ 사용)


## 프로젝트 코드 설치 순서
### 1. git clone

### 2. 이클립스 - import - Existing Gradle Project

### 3. import한 프로젝트 우클릭 - properties - Java build path - Libraries - add Library - JUnit5 추가

### 4. `build.gradle`에 JUnit5 관련 코드 추가
- `spring-boot-starter-test`에서 사용하는 일부 엔진, 라이브러리가 JUnit5에는 없어서 오류가 생길 수 있음
```gradle
// JUnit test
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation('org.springframework.boot:spring-boot-starter-test:2.2.2.RELEASE') {
    exclude group: 'junit'
    exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
}

testImplementation('org.junit.jupiter:junit-jupiter-api:5.5.2')
testImplementation('org.junit.jupiter:junit-jupiter-engine:5.5.2')
testImplementation('org.junit.jupiter:junit-jupiter-params:5.5.2')
```

### 5. 프로젝트 실행


---

## H2 데이터베이스 연결
### 1. H2 데이터베이스 설치
- [H2 다운로드 링크](https://www.h2database.com/html/download-archive.html) 반드시 **1.4.200** 버전 설치

### 2. 데이터베이스 Configuration
1. 최초 실행 시 기본 설정 그대로 connect : db정보 저장하는 파일 생성
2. 윈도우 유저 계정 홈 디렉토리에 `test.mv.db` 파일 생성 확인 : `C:\Users\윈도우유저명`
3. 파일 생성되면 url 수정 후 접속 : `jdbc:h2:tcp://localhost/~/test`
4. 아래 ddl 실행
```sql
drop table if exists member CASCADE;
create table member
(
 id bigint generated by default as identity,
 name varchar(255),
 primary key (id)
);
```
5. `프로젝트../src/main/resource/application.properties`에 DB 접속정보 작성 : 비밀번호 설정 안해도 password=까지 적어주어야 작동함
```
# h2 데이터베이스 연결
spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

### 3. 프로젝트 실행 혹은 통합테스트 실행
- 실행 전 DB인 H2가 실행되고 
- 통합테스트 : `MemberServiceIntegrationTest` 파일 실행
