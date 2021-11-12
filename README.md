# Spring-Security-Oauth

google oauth를 통해 구글 로그인 진행

db에 google 이메일이 저장 안 되어 있다면 db에 저장
비밀번호는 '1111'로 통일

소셜 로그인은 /sample/user에만 접근 가능

소셜 로그인이 아닌 일반 로그인 진행시 최대 7일 동안 쿠키 생성 가능
(소셜 로그인은 불가능)

====수정====

naver 로그인, kakao 로그인도 가능

만약 naver 아이디와 kakao 아이디가 같다면 db엔 중복되지 않게 하나만 저장됨

google, naver, kakao 이메일이 전부 다르다면 여러번 가입되는 문제점 발생 
=> 이를 어떻게 수정할지 고민해야 함
