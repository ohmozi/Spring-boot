package com.study.ohmozi.javaone.infolist.service;

import com.study.ohmozi.javaone.infolist.Repository.BlockRepository;
import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import com.study.ohmozi.javaone.infolist.domain.Block;
import com.study.ohmozi.javaone.infolist.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PersonServiceTests {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    @Test
    void getPeopleExcludeBlocks(){
        givenPeople();
        givenBlocks();

        List<Person> result = personService.getPeopleExcludeBlocks();

//        System.out.println(result);
        result.forEach(System.out::println);    //각 리스트의 객체를 한줄 씩 표현
    }


    private void givenPeople() {
        givenPerson("ohmozi", 21, "A");
        givenPerson("david", 10, "B");
        givenPerson("Evan", 24, "AB");
        givenBlockPerson("Evan", 22, "O");
    }

    private void givenBlockPerson(String name, int age, String bloodType) {
        Person blockPerson = new Person(name, age, bloodType);
        blockPerson.setBlock(givenBlock(name));
        personRepository.save(blockPerson);
    }

    private void givenBlocks() {
        givenBlock("Evan");
    }

    private void givenPerson(String name, int age, String bloodType) {
        Person person = Person.builder()
                .name(name)
                .age(age)
                .bloodType(bloodType)
                .build();

        personRepository.save(person);
    }


    private Block givenBlock(String name) {
        return blockRepository.save(new Block(name));
    }
}