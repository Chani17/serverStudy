package inu.appcenter.demo.model.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inu.appcenter.demo.domain.Member;
import inu.appcenter.demo.model.team.TeamResponse;
import lombok.Data;

@Data
public class MemberDto {

    private Long memberId;
    private String email;
    private String name;
    private int age;

    public MemberDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.age = member.getAge();

    }
}
