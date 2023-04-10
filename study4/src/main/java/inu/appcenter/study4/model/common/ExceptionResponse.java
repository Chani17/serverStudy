package inu.appcenter.study4.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private String message;
}
