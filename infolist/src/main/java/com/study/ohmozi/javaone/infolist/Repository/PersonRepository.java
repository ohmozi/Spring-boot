package com.study.ohmozi.javaone.infolist.Repository;

import com.study.ohmozi.javaone.infolist.controller.dto.PersonDto;
import com.study.ohmozi.javaone.infolist.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {


    Page<Person> findAll(Pageable pageable);
//    List<Person> findAll();

    Person save(PersonDto persondto);

    List<Person> findByName(String name);

//    List<Person> findByBlockIsNull();

//    List<Person> findByBloodType(String bloodType);

    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = ?1")
    List<Person> findByMonthOfBirthday(int monthOfBirthday);

//    @Query(value = "select person from Person person where person.birthday.monthOfBirthday =?1 and person.birthday.dayOfBirthday =?2")      //jpql
//    List<Person> findByMonthOfBirthday(int monthOfBirthday, int dayOfBirthday);

//    @Query(value = "select * from person where month_of_birthday = :monthOfBirthday and day_of_birthday =:dayOfBirthday", nativeQuery = true)
//    List<Person> findByMonthOfBirthday(int monthOfBirthday, int dayOfBirthday);
//    // SQL문 처럼 사용하기

    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday and person.birthday.dayOfBirthday = :dayOfBirthday")      //jpql
    List<Person> findByMonthAndDayOfBirthday(@Param("monthOfBirthday") int monthOfBirthday, @Param("dayOfBirthday") int dayOfBirthday);
    //방법 : 파라미터 네이밍으로 사용하기

    @Query(value = "select * from Person person where person.deleted = true", nativeQuery = true)
    // nativequery로써 h2에서 읽지않는 쿼리(where절로 처리한)를 가져올수있게한다.
    List<Person> findPeopleDeleted();

}
