# **CodeMap BackEnd**

-------------
<details>
<summary> 1. Web Server </summary>

<details>
<summary> ! 데이터 베이스 관련 주의할 점 ! </summary>
`/src/main/resource/application.properties`안에 다음과 같은 라인이 있는데,

`spring.jpa.hibernate.ddl-auto=<option>`

옵션에는 none, crate 등등 들어갈 수 있음. 항상 none으로 하는게 좋은데,
만약 데이터베이스 구조나 변수명을 바꾸었다면 create로 한번 빌드해서 서버올려야 프로젝트 내 JPA관련 파일들이 초기화가 됨(안할 시 오류남)

create는 서버올릴 때마다 연동된 DB초기화함. (내용만)
</details>

>2022-07-20 
>> 1. 초기 프로젝트 생성
>> 2. DB,API 설계에 맞게 class 구조와 컨트롤러 구현
>> 3. Data-JPA 인터페이스 사용
>> 4. 로컬 DB(MySQL)과 연동 확인
>> 5. IntelliJ 내의 HTTP Client 플러그인 사용하여 API서버 작동 확인

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
create table Algorithm(
 aid INT,
 title varchar(100),
 body varchar(10000),
 primary key(aid)
);

create table Contest(
   cid INT,
    title varchar(100),
    problemSet varchar(100),
    primary key(cid)
);

create table Problem(
   pid INT,
    cid INT,
    title varchar(100),
    memoryLimit INT,
    timeLimit float,
    body varchar(10000),
    primary key(pid)
);

create table Result(
   rid INT,
    pid INT,
    uid INT,
    executeTime float,
    usedMemory INT,
    result INT,
    usedLanguage INT,
    submitCode varchar(10000),
    submitDate datetime,
    primary key(rid)
);
```


</details>


<details>
<summary> 3. Judge Server </summary>

</details>



