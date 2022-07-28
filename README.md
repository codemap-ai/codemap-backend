# **CodeMap BackEnd**

-------------
<details>
<summary> 1. Web Server </summary>

<details>
<summary> ! 데이터 베이스 관련 주의할 점 ! </summary>

`/src/main/resource/application.properties`안에 다음과 같은 라인이 있는데,

`spring.jpa.hibernate.ddl-auto=<option>`

옵션에는 `none`, `create` 등등 들어갈 수 있음. 항상 `none`으로 하는게 좋은데,
만약 데이터베이스 구조나 변수명을 바꾸었다면 `create`로 한번 빌드해서 서버올려야 프로젝트 내 JPA관련 파일들이 초기화가 됨(안할 시 오류남)

`create`는 서버올릴 때마다 연동된 DB초기화함. (내용만)
</details>

>2022-07-20 
>> 1. 초기 프로젝트 생성
>> 2. DB,API 설계에 맞게 class 구조와 컨트롤러 구현
>> 3. Data-JPA 인터페이스 사용
>> 4. 로컬 DB(MySQL)과 연동 확인
>> 5. IntelliJ 내의 HTTP Client 플러그인 사용하여 API서버 작동 확인

>2022-07-21
>> 1. DB스키마 수정 (result -> submission , test 추가)
>> 2. API 수정
>> 3. API 서버 작동 확인

>2022-07-24
>> 1. DB스키마 수정 (contest -> problem_set , test -> contest)
>> 2. API 수정
>> 3. API 서버 작동 확인
>> 4. RabbitMQ 연동
>> 5. RabbitMQ Exchange -> Queue 메시지 전송 확인

>2022-07-25
>> 1. submission save() 확인
>> 2. /submit, /submit/submission API 생성 및 테스트 학인
>> 3. 채점서버 연동 확인

>2022-07-26
>> 1. contest save() 확인
>> 2. 채점 후 데이터베이스 채점결과 추가기능 구현

>2022-07-27
>> 1. 프론트와 Web socket 통신 구현
>> 2. 프론트 - 메인서버 - 채점서버 간의 커넥션과 DB연동 확인
>> 3. 코드 임시저장 Load_code와 API 추가
</details>

<details>
<summary> 2. Database </summary>

// 일단 MySQL 사용 (필요따라 바꿔도 ㄱㅊ)

> MySQL Configuration
>> 1. `/src/main/resource/application.properties`
>> 2. ```
>>    spring.datasource.url=jdbc:mysql://<ip>:<port>/<table>?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul
>>    spring.datasource.username=<user_id>
>>    spring.datasource.password=<password>
>>    ```


- 테이블 생성 쿼리 

```
create table algorithm(
	algorithm_id INT,
	title varchar(100),
	body varchar(10000),
	primary key(algorithm_id)
);

create table problem_set(
	problem_set_id INT,
    title varchar(100),
    problem_list varchar(100),
    primary key(problemSet_id)
);

create table problem(
	problem_id INT,
    problemSet_id INT,
    title varchar(100),
    memory_limit INT,
    time_limit float,
    body varchar(10000),
    primary key(problem_id)
);

create table submission(
    submission_id INT,
    problme_id INT,
    user_id INT,
    contest_id INT,
    execute_time float,
    used_memory INT,
    result INT,
    used_language INT,
    submit_code varchar(10000),
    submit_date datetime,
    primary key(submission_id)
);

create table contest(
	contest_id INT,
    problemSet_id INT,
    user_id INT,
    primary key(contest_id)
)

```


</details>


<details>
<summary> 3. Judge Server </summary>

</details>



