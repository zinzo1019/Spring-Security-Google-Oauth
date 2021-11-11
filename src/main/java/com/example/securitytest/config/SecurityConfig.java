package com.example.securitytest.config;

import com.example.securitytest.Security.Handler.ClubLoginSuccessHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
// Controller에서 @PreAuthorize를 사용하기 위해 선언
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin(); // 인가/인증 문제시 로그인 화면
        http.csrf().disable(); // csrf 토큰 비활성화 -> 보안을 위헤
        http.logout(); // 검색창에 logout을 치면 로그아웃 되고 다시 login 창이 뜬다.

        http.oauth2Login().successHandler(successHandler()); // Oauth를 이용한 로그인이 가능하도록

        http.rememberMe().tokenValiditySeconds(60*60*24*7) // 7일 동안 기억 (쿠키 생성)
                .userDetailsService(userDetailsService());

    }

    @Bean
    // 인증이 성공하거나 실패한 후에 처리를 지정하는 용도
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

}
