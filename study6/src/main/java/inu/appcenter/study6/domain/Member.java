package inu.appcenter.study6.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    // 회원 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원 이름
    private String name;

    // 회원 나이
    private int age;

    // 회원 상태
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public static Member createMember(String name, int age) {

        Member member = new Member();
        member.name = name;
        member.age = age;
        member.status = MemberStatus.ACTIVE;
        return member;
    }

    public static Member createMember(Long id, String name, int age) {

        Member member = new Member();
        member.id = id;
        member.name = name;
        member.age = age;
        member.status = MemberStatus.ACTIVE;
        return member;
    }

    public void update(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void delete() {
        this.status = MemberStatus.DELETE;
    }

    // 회원 생성

    // 히원 수정

    // 회원 삭제

}
