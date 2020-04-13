package com.study.ohmozi.javaone.infolist.Repository;

import com.study.ohmozi.javaone.infolist.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findAll();

    Person save(Person person);
}
