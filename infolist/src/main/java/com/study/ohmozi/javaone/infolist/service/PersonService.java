package com.study.ohmozi.javaone.infolist.service;

import com.study.ohmozi.javaone.infolist.Repository.BlockRepository;
import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import com.study.ohmozi.javaone.infolist.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j      //로그 활용
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;

    public List<Person> getPeopleExcludeBlocks(){

//        relation 추가후 findall하고 걸러내는 과정
//        List<Person> people = personRepository.findAll();
//        return people.stream().filter(person-> person.getBlock() == null).collect(Collectors.toList());
//        block 객체가 존재하는 사람이면 차단이므로 가져오지 않는다.

//        List<Block> blockList = blockRepository.findAll();
//        List<String> blockNames = blockList.stream().map(Block::getName).collect(Collectors.toList());
//        blocklist에있는 모든 값을 순회하며 name만 뽑아옴
//        이제는 onetoone relation이 걸려있기 때문에 필요없음
//        return people.stream().filter(person -> !blockNames.contains(person.getName())).collect(Collectors.toList());   //리스트타입으로 변환
//        person에 getname이 있으면 제외한다. 동일한 이름이 있으면 함께 제외되는 예외발생


//        stream과 filter로 구현해도 되지만 쿼리측면과 자원측면에서 먼저 쿼리문으로 처리해주면 더 좋음

        return personRepository.findByBlockIsNull();
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).get();

        log.info("person : {}", person);

        return person;
    }

    public List<Person> getPeopleByName(String name) {

//        List<Person> people = personRepository.findAll();
//        return people.stream().filter(person->person.getName().equals(name)).collect(Collectors.toList());
        return personRepository.findByName(name);
    }

    public List<Person> getPeopleByBloodType(String bloodType){
        return personRepository.findByBloodType(bloodType);
    }

    public List<Person> getPeopleByBirthdayMonthAndDay(int monthOfBirthday, int dayOfBirthday) {
        return personRepository.findByMonthAndDayOfBirthday(monthOfBirthday, dayOfBirthday);
    }

    public List<Person> getPeopleByBirthdayMonth(int monthOfBirthday) {
        return personRepository.findByMonthOfBirthday(monthOfBirthday);
    }
}
