## session3. 영속성컨텍스트

### 엔터티 매니저 팩토리와 엔터티 매니저
![jpa.png](..%2Fpicture%2Fjpa.png)

### 영속성 컨텍스트?
- '엔터티를 영구 저장하는 환경'
- EntityManager.persist(entity)
- 엔터티 매니저를 통해서 영속성 컨텍스트에 접근

### 엔터티 생명주기
- 비영속
  - 영속성 컨텍스트와 젼혀 관계가 없는 **새로운** 상태
- 영속
  - 영속성 컨텍스트에 **관리**되는 상태
- 준영속
  - 영속성 컨텍스트에 저장되었다가 **분리된** 상태
- 삭제
  - **삭제**된 상태

### 영속성 컨텍스트 이점
- 1차 캐시
- 동일성 보장
- 트랜잭션을 지원하는 쓰기 지연
- 변경 감지
- 지연 로딩

![관계.png](..%2Fpicture%2F%EA%B4%80%EA%B3%84.png)
- 엔터티 매니저 >= 영속성 컨텍스트 >= 1차 캐시

### 1차 캐시
- JPA는 데이터를 저장할 때 바로 db에 저장하는 것이 아니라 1차 캐시에 임시로 저장(persist)
- 트랜잭션이 커밋이 되는 시점에 db에 실제로 저장 쿼리가 나감
- 엔터티 매니저는 트랜잭션(쓰레드) 단위로 생성 후 소멸
- 조회를 할 때도 1차 캐시에 조회하고자 하는 데이터가 있는지 확인 후,
  - 만약 1차 캐시에 데이터가 있으면 그 데이터를 반환
  - 데이터가 없을 경우 DB에서 데이터 조회 -> 1차 캐시에 저장 -> 데이터 반환

### 영속 엔터티의 동일성 보장
- 1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜잭 션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공해준다. 
- 즉, 동일 멤버의 조회일 경우 비교를 하면 true를 반환

### 트랜잭션을 지원하는 쓰기 지연 
- 영속성 컨텍스트 안에는 1차 캐시와 SQL 쓰기 지연 저장소가 있다.
- em.persist()를 할 경우 바로 DB에 쿼리를 날리는 것이 아니라 차곡차곡 SQL 쓰기 지연 저장소에 쿼리가 쌓인다.
- 마지막에 tx.commit()을 할 경우 JPA에서 flush가 실행되고, db에 실제 쿼리가 날아간다.

### 변경 감지(dirty checking)
- 1차 캐시 내부에서 pk, entity, 스냅샷 3개의 데이터를 보관한다.
- 그래서 사실 최초의 데이터 값은 스냅샷을 떠둔다.
- 그리고 내부적으로 flush를 호출할 때 마지막 데이터값과 스냅샷 값을 비교한다.ㅌ
- 변경사항이 있을 경우 update 쿼리를 작성하여 db로 날린다.

### 플러시
- 영속성 컨텍스트의 변경 내용을 db에 반영함
- 플러시 하는 방법
  - em.flush()
  - 트랜잭션 커밋
  - JPQL 쿼리 실행
- 플러시를 한다고 꼭 1차 캐시가 비워지는 것은 아니다.
  - 그냥 영속성 컨텍스트와 db를 동기화 해주는 것!

### 준영속
- 영속 -> 준영속
- 영속성 컨텍스트에서 영속성 엔터티를 분리
- 준영속 상태를 만드는 방법
  - em.detach(entity)
  - em.clear()
  - em.close()

## session4. 엔터티 매핑

### @Entity
- @Entity가 붙으면 JPA가 관리하는 객체가 됨
- 기본 생성자 필수

### @Table
- 엔터티와 매핑할 테이블 지정
- 옵션
  - name : 매핑할 테이블 이름(default = entityName)
  - catalog : db catalog 에 매핑
  - schema : db schema 에 매핑
  - uniqueConstraints : ddl 생성시 유니크 제약조건 생성

### 데이터베이스 스키마 자동 생성
- DDL을 애플리케이션 실행 시점에 자동으로 생성
- 이렇게 생성된 DDL은 **개발 장비에**서만 사용해야 한다.
- hibernate.hbm2ddl.auto
  - create (drop+create)
  - create-drop, create와 같으니 종료 시점에 테이블 drop
  - update, 변경 부분만 반영
  - validate, 엔터티와 테이블이 잘 매핑되었는지 확인해줌
  - none, 사용하지 않음
- 운영장비에는 **절대로** create, create-drop, update를 사용하면 안된다!!!
- 개발 초기에는 crate or update
- 테스트(개발) 서버는 update or validate
- 스테이징과 운영 서버는 validate or none

### 필드와 컬럼 매핑

### @Enumerated
- 자바 enum 타입을 매핑할 떄 사용
- EnumType.ORDINAL 을 사용하지 말자!(String 옵션을 사용 권장)

### @Transit
- 필드 매핑 X
- DB에 저장 X, 조회 X
- 주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용한다.

### 기본키 매핑
- @Id, @GeneratedValue

### @GeneratedValue
- 생성전략이 기본적으로 4가지(default 는 AUTO)
  - TABLE, SEQUENCE, IDENTITY, AUTO;
  - AUTO일 경우 방언에 따라 기본전략이 결정

### IDENTITY 전략
- 기본 키 생성을 데이터베이스에 위임
- persist 를 해야 시퀀스를 알 수 있음
- JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
- AUTO_ INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID 값을 알 수 있음
- 버퍼링 기능(쓰기 지연) 기능을 사용할 수 없음

### SEQUENCE 전략
- 시퀀스 오브젝트를 만들어서 거기서 값을 가져옴
- allocationSize 옵션을 사용하면 한번에 시퀀스를 옵션만큼 가져올 수 있음(default = 50)
  - 이걸 통해 최적화 가능


### 권장하는 식별자 전략
- 기본키 제약 조건: not null, unique, immutable
- 권장 : Long + 대체 키 + 키 생성전략 사용

