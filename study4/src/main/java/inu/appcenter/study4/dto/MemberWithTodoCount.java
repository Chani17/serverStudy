package inu.appcenter.study4.dto;

import inu.appcenter.study4.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberWithTodoCount {

    private Member member;

    private Long count;
}
