package com.study.ohmozi.javaone.infolist.Repository;

import com.study.ohmozi.javaone.infolist.domain.Person;
import com.study.ohmozi.javaone.infolist.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


// JPA 인터페이스로 선언을 많이하는데 그래서 테스트를 따로 할 필요없다고들 한다.
// 그러나  오타 등 실수를 잡아줄 수 있다.

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
//        person.setBloodType("A");

        personRepository.save(person);

        List<Person> people = personRepository.findByName("Evan");      //evan이름을 찾아서 테스트 진행

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("Evan");

    }

    @Test
    public void findByNameIfDeleted(){          // where 절에 delete는 제외해서 결과가 나오지 않음을 테스트 할 수 있다.
        List<Person> people = personRepository.findByName("z");

        assertThat(people.size()).isEqualTo(0);
    }

    @Test
    public void findByMonthOfBirthday(){
        List<Person> people = personRepository.findByMonthOfBirthday(3);

        assertThat(people.size()).isEqualTo(2);
        assertAll(
                () -> assertThat(people.get(0).getName()).isEqualTo("c"),
                () -> assertThat(people.get(1).getBirthday().getYearOfBirthday()).isEqualTo(2000)
        );
    }

    @Test
    public void findDeletedPeople(){
        List<Person> people = personRepository.findPeopleDeleted();

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("z");

    }


//    @Test
//    void findByBloodType(){
//        List<Person> people = personRepository.findByBloodType("A");
//
//        assertThat(people.size()).isEqualTo(2);
//        assertThat(people.get(0).getName()).isEqualTo("jihun");
//        assertThat(people.get(1).getName()).isEqualTo("a");
//    }

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

    //method overiding
//    public void givenPerson(String name, int age, String bloodType){
//        givenPerson(name, age, bloodType, null);
//    }
//
//    public void givenPerson(String name, int age, String bloodType, LocalDate birthday){
//        Person person = new Person(name, age, bloodType);
//        person.setBirthday(new Birthday(birthday));
//
//        personRepository.save(person);
//    }
}