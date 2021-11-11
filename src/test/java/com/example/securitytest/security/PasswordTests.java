package com.example.securitytest.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testEncode() {

        String password = "1111";

//        암호화
        String enPw = passwordEncoder.encode(password);

        System.out.println("enPw: " + enPw);

//        암호화 되기 전 비밀번호와 암호화된 비밀번호가 근본적으로 같은지 확인
        boolean matchResult = passwordEncoder.matches(password, enPw);

        System.out.println("matchResult: " + matchResult);

    }

}
