package inu.appcenter.study7.controller;

import inu.appcenter.study7.model.MemberCreateRequest;
import inu.appcenter.study7.model.MemberResponse;
import inu.appcenter.study7.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> create(@RequestPart(required = false) MemberCreateRequest request,
                                                 @RequestPart(required = false) MultipartFile image) {

        return ResponseEntity.ok(memberService.create(image, request));
    }
}
