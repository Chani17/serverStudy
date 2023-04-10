package inu.appcenter.study4.exception.advice;

import inu.appcenter.study4.exception.AccessDeniedException;
import inu.appcenter.study4.exception.AuthenticationException;
import inu.appcenter.study4.model.common.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  ExceptionController에서 발생시킨 오류를 핸들링
 */
@RestControllerAdvice
public class ExceptionHandlingAdvice {
    // 인가 오류
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ExceptionResponse> handlingException(AccessDeniedException e) {
       return ResponseEntity
               .status(HttpStatus.FORBIDDEN)
               .body(new ExceptionResponse((e.getMessage())));
    }

    // 인증 오류
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ExceptionResponse> handlingException1(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponse((e.getMessage())));
    }
}
