package com.study.ohmozi.javaone.infolist.service;

import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import com.study.ohmozi.javaone.infolist.controller.dto.PersonDto;
import com.study.ohmozi.javaone.infolist.domain.Person;
import com.study.ohmozi.javaone.infolist.domain.dto.Birthday;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest     // 기존에 data.sql에서 db를 조회해서 테스트한 것과 달리 mock객체를 만들어 각 모듈별로 테스트한다.
@ExtendWith(MockitoExtension.class)
class PersonServiceTests {

    @InjectMocks        //테스트의 대상이되는 클래스
    private PersonService personService;

    @Mock               //personservice의 의존성을 가지고 있는 레포  : 대부분 사용하는 클래스에서 autowired하는 클래스들이라 생각
    private PersonRepository personRepository;

//    @Test
//    void getPeopleExcludeBlocks(){
//
//        List<Person> result = personService.getPeopleExcludeBlocks();
//
////        System.out.println(result);
//        result.forEach(System.out::println);    //각 리스트의 객체를 한줄 씩 표현
//    }

    @Test
    void getPeopleByName(){

//        mock의 사용 : 실제로 구현된 객체를 사용하는 것이 아니라 단순히 메소드 시그니쳐만을 사용한 테스트
//        mock을 사용하는 이유 : personRepository의 동작은 personService의 입장에서는 관심없는 기능  각자 역할에서만 단위테스트를 하기위함.
        when(personRepository.findByName("evan"))
                .thenReturn(Lists.newArrayList(new Person("evan")));
//        personRepository에서 findByname이 불렸다고 "가정"하면 아래를 리턴하라.

        List<Person> result = personService.getPeopleByName("evan");

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(java.util.Optional.of(new Person("evan")));
//        왜 optional인가? - 최근 버전에선 optional 이라는데..

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("evan");
    }

    @Test
    public void getPersonIfNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
//        왜 optional인가? - 최근 버전에선 optional 이라는데..

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }

    @Test
    public void put(){
        Person person = Person.builder()
                .name("evan")
                .hobby("programming")
                .address("seoul")
                .birthday(Birthday.of(LocalDate.now()))
                .job("student")
                .phoneNumber("010-222-5461")
                .build();

        personService.put(person);

        verify(personRepository, times(1)).save(any(Person.class));

    }

    @Test
    public void modifyIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
//        personservice의 1번아이디에 dto로 수정하겠다. 그때 런타임에러가 발생을 할 것이다를 검증하는 로직.
    }

    @Test
    public void modify(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("evan")));

        personService.modify(1L, mockPersonDto());

        verify(personRepository, times(1)).save(any(Person.class));

    }


    private PersonDto mockPersonDto() {
        return PersonDto.builder()
                .name("evan")
                .hobby("programming")
                .address("seoul")
                .birthday(LocalDate.now())
                .job("student")
                .phoneNumber("010-222-5461")
                .build();
    }
//    @Test
//    void getPerson(){
//        Person person = personService.getPerson(3L);
//        assertThat(person.getName()).isEqualTo("b");
//    }
//
//    @Test
//    void getPeopleByBloodType(){
//        List<Person> result = personService.getPeopleByBloodType("A");
//        assertThat(result.get(0).getName()).isEqualTo("jihun");
//    }
//
//    cascade의 형식을 보여주기 위한 테스트이므로 제외
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
//    }
//
//    sql로 인젝션하고 age를 생일 기반으로 계산하여 아래는 필요없음
//    private void givenPeople() {
//        givenPerson("ohmozi", 21, "A");
//        givenPerson("david", 10, "B");
//        givenPerson("Evan", 24, "AB");
//        givenBlockPerson("Evan", 22, "A");
//    }
//
//    private void givenBlockPerson(String name, int age, String bloodType) {
//        Person blockPerson = new Person(name, age, bloodType);
////        blockPerson.setBlock(givenBlock(name));
//        blockPerson.setBlock(new Block(name));
//
//        personRepository.save(blockPerson);
//    }
//
//
//    private void givenPerson(String name, int age, String bloodType) {
//        Person person = Person.builder()
//                .name(name)
//                .age(age)
//                .bloodType(bloodType)
//                .build();
//
//        personRepository.save(person);
//    }

}
