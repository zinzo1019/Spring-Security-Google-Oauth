package com.example.securitytest.Security.Service;

import com.example.securitytest.Entity.ClubMember;
import com.example.securitytest.Entity.ClubMemberRole;
import com.example.securitytest.Repository.ClubMemberRepository;
import com.example.securitytest.Security.DTO.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service // 자동으로 스프링 빈 등록
@RequiredArgsConstructor
public class ClubOauth2UserDetailsService extends DefaultOAuth2UserService {

    private final ClubMemberRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("------------------------------------");
        log.info("userRequest: " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName: " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("====================================");
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + " : " + v);
        });

//      <naver>  response에 담기는 여러 데이터들을 다시 Map 형식으로 받는다.
        Map<String, String> NaverResponse = (Map<String, String>) oAuth2User.getAttributes().get("response");

//      <kakao>
        Map<String, String> KakaoAccount = (Map<String, String>) oAuth2User.getAttributes().get("kakao_account");
        Map<String, String> KakaoProperties = (Map<String, String>) oAuth2User.getAttributes().get("properties");

        String email = null;
        String name = null;

        if(clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
        }

        if(clientName.equals("Naver")) {
            email = NaverResponse.get("email");
            name = NaverResponse.get("name");
        }

        if(clientName.equals("Kakao")) {
            email = KakaoAccount.get("email");
            name = KakaoProperties.get("nickname");
        }

        ClubMember member = saveSocialMember(email, name);

        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                member.getEmail(),
                true, //fromSocial
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        clubAuthMember.setName(member.getName());

        return clubAuthMember;

    }

    /**
     * db에 소셜 로그인 이메일과 이름 저장
     * */
    private ClubMember saveSocialMember(String email, String name) {

        Optional<ClubMember> result = repository.findByEmail(email, true);

        if (result.isPresent())
            return result.get();

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(name)
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);

        repository.save(clubMember);

        return  clubMember;

    }

}
