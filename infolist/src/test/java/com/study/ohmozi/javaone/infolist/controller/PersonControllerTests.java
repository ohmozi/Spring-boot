package com.study.ohmozi.javaone.infolist.controller;

import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
class PersonControllerTests {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    private MockMvc mockMvc;


    //mock 설정의 반복을 한번에 셋팅
    @BeforeEach     //이 메소드는 매 테스트마다 실행
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }
    @Test
    public void getPerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"name\":\"jihun\"")
                ));
    }

    @Test
    public void postPerson() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"bob\",\n" +
                                "    \"age\": 20,\n" +
                                "    \"bloodType\" : \"A\"\n" +
                                "}"))
                        .andDo(print())
                        .andExpect(status().isCreated());
    }

    @Test
    public void modifyPerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"bob\",\n" +
                                "    \"age\": 20,\n" +
                                "    \"bloodType\" : \"A\"\n" +
                                "}"))
                        .andDo(print())
                        .andExpect(status().isOk());

    }

    @Test
    public void deletePerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/4"))
                .andDo(print())
                .andExpect(status().isOk());

        log.info("deleted people -> {}", personRepository.findPeopleDeleted());

    }

}