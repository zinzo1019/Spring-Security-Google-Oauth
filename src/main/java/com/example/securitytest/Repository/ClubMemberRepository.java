package com.example.securitytest.Repository;

import com.example.securitytest.Entity.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query(" select m from ClubMember m where m.email = :email and m.fromSocial = :social ")
    Optional<ClubMember> findByEmail(@Param("email") String emial, @Param("social") boolean social);

}
