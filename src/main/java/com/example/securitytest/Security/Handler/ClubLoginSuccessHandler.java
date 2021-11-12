//package com.example.securitytest.Security.Handler;
//
//import com.example.securitytest.Security.DTO.ClubAuthMemberDTO;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Log4j2
//public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {
//
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    private PasswordEncoder passwordEncoder;
//
//    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//
//        log.info("onAuthenticationSuccess");
//
//        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO)authentication.getPrincipal();
//
//        boolean fromSocial = authMember.isFromSocial();
//
//        log.info("Need Modify Member? " + fromSocial);
//
//        if (fromSocial) {
//            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
//        }
//
//    }
//
//}
