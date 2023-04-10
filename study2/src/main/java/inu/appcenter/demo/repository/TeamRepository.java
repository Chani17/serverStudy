package inu.appcenter.demo.repository;

import inu.appcenter.demo.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    //"select t from Team t where t.name = name"; -> select * from Member m where m.name=name;
    Optional<Team> findByName(String name);

    // distinct -> 중복 제거
    @Query("select distinct t From Team t join fetch t.memberList where t.id =:teamId")
    Optional<Team> findWithMemberById(@Param("teamId") Long teamId);
}
