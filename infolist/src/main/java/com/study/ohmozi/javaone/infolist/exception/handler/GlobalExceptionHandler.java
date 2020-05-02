package com.study.ohmozi.javaone.infolist.exception.handler;

import com.study.ohmozi.javaone.infolist.exception.PersonNotFoundException;
import com.study.ohmozi.javaone.infolist.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice       // restcontroller에 전역적으로 적용되는 exceptionhandling이 가능해진다
public class GlobalExceptionHandler {
    //    아무리 시스템 오류가 나더라도 api응답은 일관성있게 나가는것이 좋다. 그래서 핸들러를 통해 status를 지정
    // 다만 controller에서 지정해주면 각 Controller에서만 적용이 된다.
    // 이렇게 되면 컨트롤러를 만들때 마다 그 컨트롤러에 맞는 핸드러를 추가해주어야하고 각 api마다 발생할수있는 핸들러를 그때 그때 코드에 추가해주어야하는 번거로움이 있다.
    // 그래서 각 컨트롤러에서 핸들링을 하지 않고 글로벌하게 컨트롤한다.

    @ExceptionHandler(PersonNotFoundException.class)
    // generic 형식
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException ex){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST); // 코드 숫자와 메세지를 받고, 헤더에 bad reqeust 추가
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // 일반적 return 형식에 헤더 붙여주는 어노테이션
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        // 오류메시지를 클라이언트에게 보여주면 보안상 문제가 있음
        // 따라서 로그에 찍기
        log.error("server error : {}", ex.getMessage(), ex);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Something wrong in server");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodNotFoundException(MethodArgumentNotValidException ex){
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getBindingResult().getFieldError());
    }
}
