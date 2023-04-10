package inu.appcenter.study3.controller;

import inu.appcenter.study3.model.member.MemberSaveRequest;
import inu.appcenter.study3.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity saveMember(@RequestBody @Valid MemberSaveRequest memberSaveRequest) {

//        if(memberSaveRequest.getPassword() == null) {
//
//        }

        Long memberId = memberService.saveMember(memberSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
    }
}
