package inu.appcenter.demo.exception.Advice;

import inu.appcenter.demo.exception.MemberException;
import inu.appcenter.demo.exception.TeamException;
import inu.appcenter.demo.model.command.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 오류가 떴을 때 다른 오류로 내준다. 500방지
@RestControllerAdvice       // 전역 컨트롤러
public class ExceptionAdvice {

    // 예외를 핸들링 -> 예외 처리
    @ExceptionHandler({TeamException.class, MemberException.class})        // 예외 클래스
    public ResponseEntity exceptionHandler(Exception e) {
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
    }
}
