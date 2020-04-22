package com.study.ohmozi.javaone.infolist.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class PersonControllerTests {

    @Autowired
    private PersonController personController;

    private MockMvc mvc;

    @Test
    public void getPerson() throws Exception {
        mvc.perform(get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}