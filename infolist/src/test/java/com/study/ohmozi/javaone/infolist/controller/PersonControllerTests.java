package com.study.ohmozi.javaone.infolist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.ohmozi.javaone.infolist.Repository.PersonRepository;
import com.study.ohmozi.javaone.infolist.controller.dto.PersonDto;
import com.study.ohmozi.javaone.infolist.exception.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
class PersonControllerTests {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    //mock 설정의 반복을 한번에 셋팅
    @BeforeEach     //이 메소드는 매 테스트마다 실행
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())      // 항상 테스트하고 프린트하기
                .build();
        //  시리얼라이즈 모듈을 테스트 파트에 추가하기 위해 변경
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person")
                    .param("page", "1")             // 1페이지 요청
                    .param("size", "2"))            // 페이지 사이즈는 2개씩
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPages").value(3))       // 디폴트값이 10개당 1페이지이므로   / size를 2로 했기에 3페이지 존재
            .andExpect(jsonPath("$.totalElements").value(6))    // 전체 개수
            .andExpect(jsonPath("$.numberOfElements").value(2)) // 한 페이지엔 2개
            .andExpect(jsonPath("$.content.[0].name").value("b"))       // 0페이지부터 시작 , 1페이지의 첫번째 인자는
            .andExpect(jsonPath("$.content.[1].name").value("c"));      // 0페이지부터 시작 , 1페이지의 두번째 인자는

//        <Paging 적용전>
//            .andExpect(jsonPath("$").value(hasSize(6)))
//             '$'는 리턴되는 객체를 뜻하므로 여기선 리스트 / 실제사이즈는 7이지만 deleted는 findall이 안읽어옴
//            .andExpect(jsonPath("$.[1].name").value("a"));
//             리스트의 두번째 값의 이름값

        // 데이터 값이 많아지면 비효율적. paging이 필요하다.
    }

    @Test
    public void getPerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("jihun"))   // 리턴되는 어트리뷰트에서 name값을 확인한다.
            .andExpect(jsonPath("$.birthday").value("1991-08-15"))

                // 우리가 원하는 모습은 1991-08-15

                // serializer추가하고 모듈 추가 후
                // "birthday":{"year":1991,"month":"AUGUST","monthValue":8,"dayOfMonth":15,"dayOfWeek":"THURSDAY","leapYear":false,"dayOfYear":227,"era":"CE","chronology":{"id":"ISO","calendarType":"iso8601"}}

                // JavaTimeModule 추가 후
                // "birthday":[1991,8,15]

                // SerializationFeature 추가 후
                // "birthday":"1991-08-15"

                // 근데 저번 테스트처럼 몇월 생일자인지 확인하려면 시리얼라이즈를 하면 안되는거아닌가?
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
                                "    \"name\": \"bob\"\n" +
                                "}"))
                    .andExpect(status().isCreated());

        assertThat(personRepository.findById(2L).get().getName()).isEqualTo("BobModify");
        // mvc perform으로 리턴값이 없으므로 레포에서 값을 가져와 확인하였다.
        // 그러나 리턴값이 있다면 바로 확인가능하다.
    }

    @Test
    public void postPersonIfNameIsNull() throws Exception {
        // 일반적인 서버 오류 핸들링
        // 입력파라미터가 잘못된경우 500보다 400 badrequest로 처리하는게 낫다.
        PersonDto dto = new PersonDto();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.message").value("Name is not be null"));
    }

    @Test
    public void postPersonIfNameIsEmptyString() throws Exception {
        PersonDto dto = new PersonDto();
        dto.setName("");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.message").value("Name is not be null"));
    }

    @Test
    public void modifyPerson() throws Exception {

        PersonDto dto = PersonDto.builder()
                .name("BobModify")
                .address("Seoul")
                .birthday(LocalDate.now())
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
//                        .content("{\n" +
//                                "    \"name\": \"BobModify\"\n" +
//                                "}"))
                    .andExpect(status().isOk());

        assertAll(
                () -> assertThat(personRepository.findById(2L).get().getName()).isEqualTo("BobModify"),
                () -> assertThat(personRepository.findById(2L).get().getAddress()).isEqualTo("Seoul")
                // 각 assertthat에서 에러가 났을때 한번에 결과를 보여준다.
        );
    }

    @Test
    public void modifyPersonIfPersonNotFound() throws Exception {
        PersonDto dto = PersonDto.builder()
                .name("james")
                .hobby("programming")
                .address("seoul")
                .birthday(LocalDate.now())
                .job("programmer")
                .phoneNumber("010-2399-2546")
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")        //없는데이터로 없는사람 확인
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Person Entity is undefined"));

    }

    @Test
    public void deletePerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/4"))
            .andExpect(status().isOk());

        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(4L)));
        // 테스트에서 직접 레포지토리를 가져와서 삭제되었는지 확인하는 방법.

        log.info("deleted people -> {}", personRepository.findPeopleDeleted());

    }

    //test 할때 json으로 보낼때 값들이 변하게되면 복잡하다 " \ 이런게 많아서
    //간단하게 하는방법
    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }

    @Test
    public void checkJsonString() throws JsonProcessingException {
        PersonDto personDto = PersonDto.builder()
                .name("evany")
                .birthday(LocalDate.now())
                .address("seoul")
                .build();

        System.out.println(">>> " + toJsonString(personDto));
//        {"name":"evany","hobby":null,"address":"seoul","birthday":"2020-04-25","job":null,"phoneNumber":null}
//        이 테스트를 통해 빠르고 확실하게 json형식으로 생성가능
    }
}