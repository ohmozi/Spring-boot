package com.study.ohmozi.javaone.infolist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//@ResponseBody
@RestController     //restcontroller가 controller와 responsebody를 포함하느 ㄴ어노테이션이다
@Slf4j
public class HelloController {

    @GetMapping("/api/helloWorld")
    public String index(){
        return "Hello world!";
    }

    @GetMapping("/api/helloException")      //항상 오류를 발생시키는 api
    public String helloException(){
        throw new RuntimeException("Hello RuntimeException");
    }
}
