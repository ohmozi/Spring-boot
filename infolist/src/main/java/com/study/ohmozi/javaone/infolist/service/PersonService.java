package com.study.ohmozi.javaone.infolist.service;

import com.study.ohmozi.javaone.infolist.Repository.BlockRepository;
import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import com.study.ohmozi.javaone.infolist.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;

    public List<Person> getPeopleExcludeBlocks(){
        List<Person> people = personRepository.findAll();
//        List<Block> blockList = blockRepository.findAll();
//        List<String> blockNames = blockList.stream().map(Block::getName).collect(Collectors.toList());
//        blocklist에있는 모든 값을 순회하며 name만 뽑아옴
//        이제는 onetoone relation이 걸려있기 때문에 필요없음
//        return people.stream().filter(person -> !blockNames.contains(person.getName())).collect(Collectors.toList());   //리스트타입으로 변환
//        person에 getname이 있으면 제외한다. 동일한 이름이 있으면 함께 제외되는 예외발생
        return people.stream().filter(person-> person.getBlock() == null).collect(Collectors.toList());
        //block 객체가 존재하는 사람이면 차단이므로 가져오지 않는다.
    }
}
