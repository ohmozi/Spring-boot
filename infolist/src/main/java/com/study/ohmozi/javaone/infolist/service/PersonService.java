package com.study.ohmozi.javaone.infolist.service;

import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import com.study.ohmozi.javaone.infolist.controller.dto.PersonDto;
import com.study.ohmozi.javaone.infolist.domain.Person;
import com.study.ohmozi.javaone.infolist.domain.dto.Birthday;
import com.study.ohmozi.javaone.infolist.exception.PersonNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@Slf4j      //로그 활용
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

//      public List<Person> getPeopleExcludeBlocks(){

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

//        return personRepository.findByBlockIsNull();
//    }


    public Page<Person> getAll(Pageable pageable) {     // springframwork의 pageable
        return personRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
//        Person person = personRepository.findById(id).get();
        Person person = personRepository.findById(id).orElse(null);     //get을 하는데 없으면 null 리턴. 예외처리가능

        log.info("person : {}", person);

        return person;
    }

    @Transactional
    public void put(PersonDto personDto){
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());

        personRepository.save(person);
    }

    public List<Person> getPeopleByName(String name) {

//        List<Person> people = personRepository.findAll();
//        return people.stream().filter(person->person.getName().equals(name)).collect(Collectors.toList());
        return personRepository.findByName(name);
    }

//    public List<Person> getPeopleByBloodType(String bloodType){
//        return personRepository.findByBloodType(bloodType);
//    }

    public List<Person> getPeopleByBirthdayMonthAndDay(int monthOfBirthday, int dayOfBirthday) {
        return personRepository.findByMonthAndDayOfBirthday(monthOfBirthday, dayOfBirthday);
    }

    public List<Person> getPeopleByBirthdayMonth(int monthOfBirthday) {
        return personRepository.findByMonthOfBirthday(monthOfBirthday);
    }

    @Transactional
    public void modify(Long id, @Valid PersonDto personDto) {

        Person personAtDb = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);        // 람다식으로 변경한건데 이해가 잘안됨..

        personAtDb.setName(personDto.getName());
        personAtDb.setHobby(personDto.getHobby());
        personAtDb.setAddress((personDto.getAddress()));
        if ( personDto.getBirthday() != null){          // 생일 객체가 null일수도 있으므로
            personAtDb.setBirthday(Birthday.of(personDto.getBirthday()));
        }

        personRepository.save(personAtDb);
    }

    @Transactional
    public void delete(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        person.setDeleted(true);

        personRepository.save(person);
//      사실 그냥 지우는 법은 없음.  잘못 들어왔을때 지워버리면 복구방법이 없다.
//        personRepository.deleteById(id);      //이렇게 그냥 지워도됨
    }

}
