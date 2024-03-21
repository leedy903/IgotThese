# IgotThese

## 소개

> 포켓몬 씰 도감 및 교환 커뮤니티 어플리케이션 백엔드 저장소

## 목차

* [개요](#개요)
* [API](#API)

## 개요

### 정보
* 프로젝트 이름: IgotThese
* 프로젝트 기간: 2024.01.10 ~ - 진행중 - 
* 사용 언어 및 개발환경: Java, Spring boot, JPA, MySQL

## API

### User
| 요청 URL                                |메서드|설명|
|---------------------------------------|:---:|---|
| api/v1/user/create                    |POST|유저 생성|
| api/v1/user/info/?userName={userName} |GET|유저 정보 조회|
| api/v1/user/edit                      |PATCH|유저 정보 수정|
| api/v1/user/delete/?id={id}           |DELETE|유저 삭제|

### Post
| 요청 URL                      |메서드|설명|
|-----------------------------|:---:|---|
| api/v1/post/create          |POST|게시글 생성|
| api/v1/post/posts           |GET|모든 게시글 조회|
| api/v1/post/posts           |POST|게시글 검색|
| api/v1/post/edit            |PATCH|게시글 정보 수정|
| api/v1/post/delete/?id={id} |DELETE|게시글 삭제|

### Book
| 요청 URL                              |메서드|설명|
|-------------------------------------|:---:|---|
| api/v1/book/mybook/?userId={userId} |GET|유저 도감 조회|
| api/v1/book/ranker/?userId={userId} |GET|상위 10위 유저 도감 조회|