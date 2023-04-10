package inu.appcenter.study6.controller;

import inu.appcenter.study6.domain.Member;
import inu.appcenter.study6.model.MemberCreateRequest;
import inu.appcenter.study6.model.MemberResponse;
import inu.appcenter.study6.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> saveMember(@RequestBody MemberCreateRequest request) {

        Member savedMember = memberService.saveMember(request);

        return ResponseEntity.ok(MemberResponse.from(savedMember));
    }
}
