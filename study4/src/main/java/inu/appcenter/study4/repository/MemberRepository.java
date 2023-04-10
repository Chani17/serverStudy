package inu.appcenter.study4.repository;
;
import inu.appcenter.study4.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Query("select distinct m from Member m left join fetch m.roles where m.id=:memberId")
    Optional<Member> findWithRolesById(@Param("memberId") Long memberId);

}