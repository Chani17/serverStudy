package inu.appcenter.demo.repository;

import inu.appcenter.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    // JQL
    @Query("select m From Member m join fetch m.team where m.id =:memberId")
    // SQL : select * from Member m,team t where m.team_id = m.id and m.id = ?
    Optional<Member> findWithTeamById(@Param("memberId") Long memberId);

    @Query("select m FROM Member m join fetch m.team")
    List<Member> findWithTeamAll();
}
