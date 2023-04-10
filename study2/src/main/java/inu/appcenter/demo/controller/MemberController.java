package inu.appcenter.demo.controller;

import inu.appcenter.demo.domain.Member;
import inu.appcenter.demo.model.member.MemberResponse;
import inu.appcenter.demo.model.member.MemberSaveRequest;
import inu.appcenter.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/teams/{teamId}/members")
    public ResponseEntity saveMember(@PathVariable Long teamId,
                                     @RequestBody MemberSaveRequest memberSaveRequest) {
        memberService.save(teamId, memberSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 수정
    // 삭제

    // 조회
    @GetMapping("/members/{memberId}")
    public MemberResponse findById(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);

        return new MemberResponse(member);
    }

    // 여러개 조회
    @GetMapping("/members")
    public List<MemberResponse> findAll() {
        List<Member> members = memberService.findAll();
        List<MemberResponse> memberResponseList = members.stream()
                .map(member -> new MemberResponse(member))
                .collect(Collectors.toList());

        return memberResponseList;
    }
}
