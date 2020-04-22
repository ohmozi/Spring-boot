package com.study.ohmozi.javaone.infolist.Repository;

import com.study.ohmozi.javaone.infolist.domain.Person;
import com.study.ohmozi.javaone.infolist.domain.dto.Birthday;
import com.study.ohmozi.javaone.infolist.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

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
        person.setBloodType("A");

        personRepository.save(person);

        List<Person> people = personRepository.findByName("Evan");      //evan이름을 찾아서 테스트 진행

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("Evan");
        assertThat(people.get(0).getAge()).isEqualTo(21);

    }

//    @Test
//    public void hashCodeEquals(){
//
//        Person person1 = new Person("evan", 21,"A");
//        Person person2 = new Person("evan", 21,"A");
//
//        System.out.println(person1.equals(person2));
//        System.out.println(person1.hashCode());
//        System.out.println(person2.hashCode());
//
//        Map<Person, Integer> map = new HashMap<>();
//        map.put(person1, person1.getAge());
//
//        System.out.println(map);
//        System.out.println(map.get(person2));
//    }

    @Test
    void findByBloodType(){
        List<Person> people = personRepository.findByBloodType("A");

        assertThat(people.size()).isEqualTo(2);
        assertThat(people.get(0).getName()).isEqualTo("jihun");
        assertThat(people.get(1).getName()).isEqualTo("a");
    }

    @Test
    void getPeopleByBirthdayBetween(){
        List<Person> result = personRepository.findByMonthOfBirthday(3);
//        해달 월 생일자 찾기
//        List<Person> result = personService.getPeopleByBirthdayMonthAndDay(3, 6);
//        해달 월,일 생일자 찾기

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("c");
        assertThat(result.get(1).getName()).isEqualTo("d");
//        result.forEach(System.out::println);
    }

    //method overiding
    public void givenPerson(String name, int age, String bloodType){
        givenPerson(name, age, bloodType, null);
    }

    public void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = new Person(name, age, bloodType);
        person.setBirthday(new Birthday(birthday));

        personRepository.save(person);
    }
}