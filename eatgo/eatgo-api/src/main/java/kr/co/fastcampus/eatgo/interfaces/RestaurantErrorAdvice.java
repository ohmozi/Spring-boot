package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//interfaces에 선언해주어야함 이유는 모르겟음;
//에러처리 핸들러 정의
@ControllerAdvice
public class RestaurantErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class)
    public String handleNotFound(){
        return "{}";    //@ResponseBody필요
//        404 not found처리는 했지만 컨텐츠에 무엇인가를 표현하고 싶을때 이런 방식활용가능
    }
}
