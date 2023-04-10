package inu.appcenter.study7.repository;

import inu.appcenter.study7.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
