package com.study.ohmozi.javaone.infolist.Repository;

import com.study.ohmozi.javaone.infolist.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

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
}