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

        String email = null;

        if(clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        ClubMember member = saveSocialMember(email);
        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                true, //fromSocial
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        clubAuthMember.setName(member.getName());

        return clubAuthMember;

    }

    private ClubMember saveSocialMember(String email) {

        Optional<ClubMember> result = repository.findByEmail(email, true);

        if (result.isPresent())
            return result.get();

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .password("1111")
                .name(email)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);

        repository.save(clubMember);

        return  clubMember;

    }

}
