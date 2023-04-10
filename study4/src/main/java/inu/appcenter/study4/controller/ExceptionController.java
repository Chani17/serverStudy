package inu.appcenter.study4.controller;

import inu.appcenter.study4.exception.AccessDeniedException;
import inu.appcenter.study4.exception.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    /**
     * 인증 및 인가 오류가 발생했을 대 redirect가 오는 컨트롤러
     * 에러를 낸다.
     * @return
     */
    @GetMapping("/exception/entrypoint")
    public ResponseEntity authenticationException() {
        throw new AuthenticationException("인증 오류가 발생하였습니다.");
    }

    @GetMapping("/exception/accessdenied")
    public ResponseEntity accessDeniedException() {
        throw new AccessDeniedException("권한이 없습니다.");
    }
}
