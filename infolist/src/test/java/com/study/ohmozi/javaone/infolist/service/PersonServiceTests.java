package com.study.ohmozi.javaone.infolist.service;

import com.study.ohmozi.javaone.infolist.Repository.BlockRepository;
import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import com.study.ohmozi.javaone.infolist.domain.Block;
import com.study.ohmozi.javaone.infolist.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

        List<Person> result = personService.getPeopleExcludeBlocks();

//        System.out.println(result);
        result.forEach(System.out::println);    //각 리스트의 객체를 한줄 씩 표현
    }

    @Test
    void getPeopleByName(){

        List<Person> result = personService.getPeopleByName("b");
        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    void getPeopleByBloodType(){
        List<Person> result = personService.getPeopleByBloodType("A");
        assertThat(result.get(0).getName()).isEqualTo("jihun");
    }


    @Test
    void getPerson(){
        Person person = personService.getPerson(3L);
        assertThat(person.getName()).isEqualTo("b");
    }

    //cascade의 형식을 보여주기 위한 테스트이므로 제외
//    @Test
//    void cascadeTest(){
//        givenPeople();
//
//        List<Person> result = personRepository.findAll();
//
//        result.forEach(System.out::println);
//
//        Person person = result.get(3);
//        person.getBlock().setStartDate(LocalDate.now());
//        person.getBlock().setEndDate(LocalDate.now());
//
//        personRepository.save(person);
//        personRepository.findAll().forEach(System.out::println);
//
////        personRepository.delete(person);
////        personRepository.findAll().forEach(System.out::println);
////        blockRepository.findAll().forEach(System.out::println);
//
//        person.setBlock(null);
//        personRepository.save(person);
//        personRepository.findAll().forEach(System.out::println);
//        blockRepository.findAll().forEach(System.out::println);
//
//    }

    private void givenPeople() {
        givenPerson("ohmozi", 21, "A");
        givenPerson("david", 10, "B");
        givenPerson("Evan", 24, "AB");
        givenBlockPerson("Evan", 22, "A");
    }

    private void givenBlockPerson(String name, int age, String bloodType) {
        Person blockPerson = new Person(name, age, bloodType);
//        blockPerson.setBlock(givenBlock(name));
        blockPerson.setBlock(new Block(name));

        personRepository.save(blockPerson);
    }


    private void givenPerson(String name, int age, String bloodType) {
        Person person = Person.builder()
                .name(name)
                .age(age)
                .bloodType(bloodType)
                .build();

        personRepository.save(person);
    }
}