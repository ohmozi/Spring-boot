package com.study.ohmozi.javaone.infolist.Repository;

import com.study.ohmozi.javaone.infolist.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findAll();

    Person save(Person person);

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
    List<Person> findPeopleDeleted();
}
