package Happy20.GrowingPetPlant.Exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Map<String, ?>  handleConstraintViolationException(ConstraintViolationException e) {
        // 유효성 검사에서 발생한 첫 번째 필드 에러의 기본 메시지만 반환
        String errorMessage = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violation -> violation.getMessage())
                .orElse("Validation failed");
        return Map.of("message", errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String, ?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 유효성 검사에서 발생한 첫 번째 필드 에러의 기본 메시지만 반환
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation failed");
        return Map.of("message", errorMessage);
    }
}