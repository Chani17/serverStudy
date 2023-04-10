package inu.appcenter.study4.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.appcenter.study4.domain.Member;
import inu.appcenter.study4.domain.QMember;
import inu.appcenter.study4.domain.QTodo;
import inu.appcenter.study4.domain.TodoStatus;
import inu.appcenter.study4.dto.MemberWithTodoCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.count;
import static inu.appcenter.study4.domain.QMember.*;
import static inu.appcenter.study4.domain.QTodo.*;

@RequiredArgsConstructor
@Repository     //  스프링 빈으로 등록, 데이터베이스 오류나 JPA 오류를 스프링의 오류로 전환해줘서 에러설명이 깔끔하다.
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;     // 우리가 등록한 JPAQueryFactory

    public Member findMemberById(Long memberId) {
        Member result = queryFactory
                //"select m from Member m left join fetch m.todoList tl where m.id=:memberId and tl.status="
                .select(member).distinct()
                .from(member)
                .leftJoin(member.todoList, todo).fetchJoin()
                .where(member.id.eq(memberId).and(
                        todo.status.eq(TodoStatus.NOTFINISH)))        // NOTFINISH만 가지고 온다.
                .fetchOne();        // 한개의 결과 값이 확실할때만 사용
                                    // 반환 List -> fetch()

        return result;
    }

    public MemberWithTodoCount findMemberWithTodoCountsById(Long memberId) {
        Tuple tuple = queryFactory.select(member, todo.count())
                .from(member)
                .leftJoin(member.todoList, todo)
                .groupBy(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        MemberWithTodoCount memberWithTodoCount = new MemberWithTodoCount();
        memberWithTodoCount.setMember(tuple.get(member));
        memberWithTodoCount.setCount(tuple.get(todo.count()));
//        Member findMember = tuple.get(QMember.member);
//        Long count = tuple.get(QTodo.todo.count());

        return memberWithTodoCount;
    }

    public List<Member> findMemberWithTodoById() {
        List<Member> result = queryFactory
                .select(member).distinct()
                .from(member)
                .leftJoin(member.todoList, todo).fetchJoin()
                .fetch();

        return result;
    }
}
