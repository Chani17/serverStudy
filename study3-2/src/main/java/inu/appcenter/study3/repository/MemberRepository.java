package inu.appcenter.study3.repository;

import inu.appcenter.study3.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    // 회원을 조회할 때 회원의 주문목록까지 같이 조회
    // 일 대 다 조회 -> 일 대 다 조인이 발생
    @Query("select distinct m from Member m join fetch m.orderList where m.id=:memberId")
    Optional<Member> findWithOrderListById(@Param("memberId") Long memberId);

    // 주문하는 거

    // 회원 리스트를 조회할 때 모든 회원의 주문목록까지 같이 조회
    // 위에거랑 비슷...
    // 오류가 난다. 왜일까? 오류 나는 이유 -> 코드 변경하고 주석으로 적기
    @Query("select distinct m from Member m join fetch m.orderList") // -> 위에서 where만 제외하면 될듯
    Optional<Member> findWithOrderListAll();
}
