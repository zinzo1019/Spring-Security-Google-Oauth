# Spring-Security-Google-Oauth

google oauth를 통해 구글 로그인 진행

db에 google 이메일이 저장 안 되어 있다면 db에 저장
비밀번호는 '1111'로 통일

소셜 로그인은 /sample/user에만 접근 가능

소셜 로그인이 아닌 일반 로그인 진행시 최대 7일 동안 쿠키 생성 가능
(소셜 로그인은 불가능)
