package com.example.securitytest.Controller;

import com.example.securitytest.Security.DTO.ClubAuthMemberDTO;
import lombok.extern.java.Log;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log
@RequestMapping("/sample/")
public class SampleController {

        @PreAuthorize("permitAll()") // 모든 사용자가 접근 가능
        @GetMapping("/all")
        public void exAll() {
            log.info("exAll...");
        }

        @PreAuthorize("hasRole('USER')") // USER 권한이 있는 사용자만 접근 가능 (로그인 성공 시 USER)
        @GetMapping("/user")
        public void exUser(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {

            log.info("exUser...");

    }

    @PreAuthorize("hasRole('MEMBER')") // MEMBER 권한이 있는 사용자만 접근 가능
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {

        log.info("exMember...");

//        log.info((Supplier<String>) clubAuthMemberDTO);

    }

    @PreAuthorize("hasRole('ADMIN')") // ADMIN 권한이 있는 사용자만 접근 가능
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin...");
    }

}
