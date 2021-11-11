package com.example.securitytest.security;

import com.example.securitytest.Entity.ClubMember;
import com.example.securitytest.Entity.ClubMemberRole;
import com.example.securitytest.Repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTest {

    @Autowired
    ClubMemberRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            ClubMember clubMember = ClubMember.builder()
                    .email("email..." + i)
                    .name("user..." + i)
//                    password 암호화 하지 않으면 나중에 로그인 할 때 password와 db의 password를 매칭하지 못한다.
                    .password(passwordEncoder.encode("1111"))
                    .fromSocial(false)
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

//            user80부터 user89까지 권한: USER, MEMBER
            if (i >= 80) {
                clubMember.addMemberRole(ClubMemberRole.MEMBER);
            }

//            user90부터 user100까지 권한: USER, MEMBER, ADMIN
            if (i >= 90) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            repository.save(clubMember);

        });

    }

    @Test
    public void testRead() {

        Optional<ClubMember> result = repository.findByEmail("email...83", false);

        ClubMember clubMember = result.get();

        System.out.println(clubMember);

    }

}
