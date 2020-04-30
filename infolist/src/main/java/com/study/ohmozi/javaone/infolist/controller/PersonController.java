package com.study.ohmozi.javaone.infolist.controller;

import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import com.study.ohmozi.javaone.infolist.controller.dto.PersonDto;
import com.study.ohmozi.javaone.infolist.domain.Person;
import com.study.ohmozi.javaone.infolist.domain.exception.PersonNotFoundException;
import com.study.ohmozi.javaone.infolist.domain.exception.dto.ErrorResponse;
import com.study.ohmozi.javaone.infolist.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//        PersonDto personDto = PersonDto.builder()
//                .name(person.getName())
//                .address(person.getAddress())
//                .hobby(person.getHobby())
//                .build();           // 생성자 늘리기

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

    //    아무리 시스템 오류가 나더라도 api응답은 일관성있게 나가는것이 좋다. 그래서 핸들러를 통해 status를 지정
    @ExceptionHandler(value = PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException ex){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST); // 코드 숫자와 메세지를 받고, 헤더에 bad reqeust 추가
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
