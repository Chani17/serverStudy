package inu.appcenter.study6.repository;

import inu.appcenter.study6.domain.Member;
import inu.appcenter.study6.domain.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest     // 통합 테스트 x
@DataJpaTest         // repository 계층만 가져온다.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)     // ANY : 내장 DB -> 메모리, NONE : 실제 dB 접근
class MemberRepositoryTest {

// 오류가 떴을 때 transactional이 걸려있으면 roll back이 실행된다.

    @Autowired
    private MemberRepository memberRepository;

    //@Transactional 테스트 코드에 @Transactional 테스트 코드 실행 후 롤백
    // 다른 테스트를 실행 시킬때 데이터 베이스에 값이 이미 저장되어 있어 깨질 수가 있다.
    @Test
    @DisplayName("회원 저장")
    public void saveMember() {
        // given
        Member member = Member.createMember("김찬희", 23);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertNotNull(savedMember.getId());
    }

    @Test
    @DisplayName("회원 단일 조회")
    public void findMember(){
        // given
        Member member = Member.createMember("김찬희", 23);
        Member savedMember = memberRepository.save(member);

        // when
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        // then
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getName()).isEqualTo(savedMember.getName());
        assertThat(findMember.getAge()).isEqualTo(savedMember.getAge());
        assertThat(findMember.getStatus()).isEqualTo(savedMember.getStatus());
    }

    @Test
    @DisplayName("회원 목록 조회")
    public void findMembers() {
        // given
        Member member1 = Member.createMember("김찬희", 23);
        Member member2 = Member.createMember("황주환", 25);
        Member member3 = Member.createMember("정윤아", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        // when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("삭제 회원 조회")
    public void findDeleteMember() {
        // given ( 어떠한 환경, 상황에서 )
        Member member1 = Member.createMember("김찬희", 23);
        Member member2 = Member.createMember("황주환", 25);
        Member member3 = Member.createMember("정윤아", 20);
        member3.delete();
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        // when ( 이러한 행동을 했을 때 )
        Member findMember = memberRepository.findByStatus(MemberStatus.DELETE).get();

        // then ( 이러한 결과가 나올 것이다 )
        assertThat(findMember.getName()).isEqualTo(member3.getName());
    }

    // BDD 행위 주도 개발
}