package com.example.securitytest.Entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ClubMember extends BaseEntity {

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();

    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    public void addMemberRole(ClubMemberRole clubMemberRole) {

        roleSet.add(clubMemberRole);

    }

}
