package com.study.ohmozi.javaone.infolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//@ResponseBody
@RestController     //restcontroller가 controller와 responsebody를 포함하느 ㄴ어노테이션이다
public class HelloController {
    @GetMapping("/index")
    public String index(){
        return "Hello world!";
    }
}
