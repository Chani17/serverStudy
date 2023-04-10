package inu.appcenter.study3.repository;

import inu.appcenter.study3.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Commit
    @Test
    void MemberTest() {
        Member member = Member.createMember("cucute8878@naver.com", "8888", "김찬희");
        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
    }
}