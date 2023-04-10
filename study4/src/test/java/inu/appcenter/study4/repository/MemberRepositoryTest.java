package inu.appcenter.study4.repository;

import inu.appcenter.study4.domain.Member;
import inu.appcenter.study4.domain.Todo;
import inu.appcenter.study4.dto.MemberWithTodoCount;
import inu.appcenter.study4.repository.query.MemberQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@Transactional // 모든 테스트가 끝나고 디비에 데이터를 초기화 -> rollback
//@DataJpaTest    //data access계층의 빈들을 가지고 온다. EntityManager, DataSource, @Repository 빈들을 가지고 온다.
@SpringBootTest
@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // h2 내장 디비로 테스트
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("회원 저장 테스트")
    void Test() {
        Member member = Member.createMember("cucute8878@naver.com", "8888", "김찬희");
        Member savedMember = memberRepository.save(member);

        // 내가(파라미터가) 아마 null이 아닐거야~
        assertNotNull(savedMember.getId());
        //assertThat(savedMember.getId())/
    }


    // 회원 - 글 ( 글의 상태가 DELETE가 아닌 글만 가져와라
    @Test
    @DisplayName("회원 - todo 같이 조회")
    void findMemberById() {
        Member member = Member.createMember("cucute8878@naver.com", "8888", "김찬희");
        memberRepository.save(member);

        Todo todo1 = Todo.createTodo("앱센터 스터디", member);
        Todo todo2 = Todo.createTodo("데이터베이스 스터디", member);
        Todo todo3 = Todo.createTodo("서버스터디", member);

        todo3.changeStatus();
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        em.flush();     // 영속성 컨텍스트에 있는 쿼리 날라감
        em.clear();     // 영속성 컨텍스트 초기화

        Member findMember = memberQueryRepository.findMemberById(1L);

        assertThat(findMember.getTodoList().size()).isEqualTo(2);
    }

    // 글을 조회하면서 글을 댓글수를 찾아오는 방법?
    // 회원을 조회하면서 todo의 갯수를 조회
    @Test
    @DisplayName("회원 조회 - todo 갯수")
    void findMemberWithTodoCountsById() {
        Member member = Member.createMember("cucute8878@naver.com", "8888", "김찬희");
        memberRepository.save(member);

        Todo todo1 = Todo.createTodo("앱센터 스터디", member);        // NOTFINISH
        Todo todo2 = Todo.createTodo("데이터베이스 스터디", member);   // NOTFINISH
        Todo todo3 = Todo.createTodo("서버스터디", member);
        todo3.changeStatus();       // FINISH
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        em.flush();     // 영속성 컨텍스트에 있는 쿼리 날라감
        em.clear();     // 영속성 컨텍스트 초기화

        MemberWithTodoCount result = memberQueryRepository.findMemberWithTodoCountsById(1L);

        assertThat(result.getCount()).isEqualTo(3);
    }
}