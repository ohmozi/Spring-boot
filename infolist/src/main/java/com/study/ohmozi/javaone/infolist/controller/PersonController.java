package com.study.ohmozi.javaone.infolist.controller;

import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import com.study.ohmozi.javaone.infolist.controller.dto.PersonDto;
import com.study.ohmozi.javaone.infolist.domain.Person;
import com.study.ohmozi.javaone.infolist.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/person")
@RestController
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    //    @GetMapping()
//    @RequestMapping(value = "/{Id}")
    @GetMapping("/{Id}")
    public Person getPerson(@PathVariable Long Id){

        return personService.getPerson(Id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@Valid @RequestBody Person person){
        personService.put(person);

        log.info("person -> {}", personRepository.findAll());

    }

    //    @PatchMapping("/{id}")      //put과의 차이 : 일부만 업데이트한다
    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id,@Valid @RequestBody PersonDto personDto){


        personService.modify(id, personDto);

        log.info("person -> {}", personRepository.findAll());

    }



    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);

        log.info("person -> {}", personRepository.findAll());

    }
}
