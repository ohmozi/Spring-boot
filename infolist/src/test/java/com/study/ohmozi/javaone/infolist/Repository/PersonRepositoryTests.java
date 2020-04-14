package com.study.ohmozi.javaone.infolist.Repository;

import com.study.ohmozi.javaone.infolist.domain.Person;
import com.study.ohmozi.javaone.infolist.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Test
    public void crud(){
        Person person = new Person();
        person.setName("Evan");
        person.setAge(21);

        personRepository.save(person);

        System.out.println(personRepository.findAll());
        List<Person> people = personRepository.findAll();

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("Evan");
        assertThat(people.get(0).getAge()).isEqualTo(21);

    }

    @Test
    public void hashCodeEquals(){

        Person person1 = new Person("evan", 21,"A");
        Person person2 = new Person("evan", 21,"A");

        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        Map<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2));
    }


    @Test
    void getPeopleByBirthdayBetween(){
        givenPerson("a", 6, "A", LocalDate.of(2015,12,3));
        givenPerson("b", 10, "B", LocalDate.of(2013,7,2));
        givenPerson("c", 11, "O", LocalDate.of(2008,2,18));
        givenPerson("d", 5, "AB", LocalDate.of(2012,9,24));
        givenPerson("e", 8, "B", LocalDate.of(2006,3,31));


        List<Person> result = personService.getPeopleByBirthdayBetween(LocalDate.of(2010, 10,8), LocalDate.of(2020,12,15));

        result.forEach(System.out::println);
    }

    //method overiding
    public void givenPerson(String name, int age, String bloodType){
        givenPerson(name, age, bloodType, null);
    }

    public void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = new Person(name, age, bloodType);
        person.setBirthday(birthday);

        personRepository.save(person);
    }
}