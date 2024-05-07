# mju-likelion-memoapp-project
멋사 세션 과제_메모장 관리 API 서버 만들기

메모장 관리 API 서버 만들기 

비즈니스 로직
1) 모든 유저가 자신의 메모 전체 내용 조회
2) 메모id와 유저id를 통해 자신의 특정 메모를 조회
3) 새로운 메모 생성
4) 메모id와 유저id를 통해 자신의 특정 메모 삭제
5) 메모id와 유저id를 통해 자신의 특정 메모 수정 (단, ID는 변경 불가능)
6) 좋아요 기능 구현
7) 좋아요 정보 조회 (좋아요 누른 시간, 좋아요 누른 user의 id, 좋아요 총 수)

API설계
1️⃣Memo
1) 모든 유저가 메모 전체 내용 조회
- URI : /memos
- HTTP 메서드 : GET

2) ID로 특정 메모 조회
- URI : /memos/{id}
- HTTP 메서드 : GET

3) 메모 생성
-URI : /memos
-HTTP 메서드 : POST

3) ID로 특정 메모 삭제
- URI : /memos/{id}
- HTTP 메서드 : DELETE

4) ID로 특정 메모 수정
- URI : /memos/{id}
- HTTP 메서드 : PATCH

5) 메모 좋아요 기능
- URI : /memos/{id}/likes
- HTTP 메서드 : POST

6) 좋아요 리스트 조회
- URI : /memos/likes
- HTTP 메서드 : GET

2️⃣User

1) 회원가입
- URI : /users
- HTTP 메서드 : POST

2) 회원탈퇴
- URI : /users
- HTTP 메서드 : DELETE
-RequestHeader로 userId받기

3) 회원정보 수정
-URI : /users
-HTTP 메서드 : PATCH
-RequestHeader로 userId받기

3) 로그인
- URI : /login
- HTTP 메서드 : POST

3️⃣Organization

1) 가입
- URI : /organizations
- HTTP 메서드 : POST
-RequestHeader로 userId받기

2) 탈퇴
- URI : /organizations
- HTTP 메서드 : DELETE
-RequestHeader로 userId받기
