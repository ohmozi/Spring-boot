package com.study.ohmozi.javaone.infolist.controller;

import com.study.ohmozi.javaone.infolist.domain.Person;
import com.study.ohmozi.javaone.infolist.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/person")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping()
    @RequestMapping(value = "/{Id}")
//    @GetMapping("/{Id}")
    public Person getPerson(@PathVariable Long Id){
        return personService.getPerson(Id);
    }
}
