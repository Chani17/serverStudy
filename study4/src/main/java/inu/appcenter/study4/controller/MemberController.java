package inu.appcenter.study4.controller;

import inu.appcenter.study4.config.security.JwtTokenProvider;
import inu.appcenter.study4.domain.Member;
import inu.appcenter.study4.model.member.MemberLoginRequest;
import inu.appcenter.study4.model.member.MemberResponse;
import inu.appcenter.study4.model.member.MemberSaveRequest;
import inu.appcenter.study4.model.todo.TodoUpdateRequest;
import inu.appcenter.study4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/members")
    // ResponseEntity에도 타입이 존재한다?
    public ResponseEntity<Void> saveMember(@RequestBody MemberSaveRequest memberSaveRequest) {
        memberService.saveMember(memberSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/members/login")
    public ResponseEntity<String> loginMember(@RequestBody MemberLoginRequest memberLoginRequest) {
        //String jwtToken = memberService.loginMember(memberLoginRequest);
        /**
         * 1.서버에 저장된 회원 정보와 로그인 요청 정보가 일치하는지 확인
         */
        Member findMember = memberService.loginMember(memberLoginRequest);

        /**
         * 3. 회원 ID를 사용하여 토큰 생성 메서드 호출
         */
        String jwtToken =  jwtTokenProvider.createToken(String.valueOf(findMember.getId()));

        /**
         * 5. JWT 토큰을 사용자에게 전달
         */
        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/members")
    public ResponseEntity findMembers() {

        List<Member> members = memberService.findMembers();
        List<MemberResponse> result = members.stream()
                .map(member -> MemberResponse.from(member))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


}
