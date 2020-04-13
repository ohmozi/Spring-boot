package com.study.ohmozi.javaone.infolist;

import com.study.ohmozi.javaone.infolist.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
class InfolistApplicationTests {

	@Autowired
	private HelloController helloController;

	private MockMvc mvc;

	//juni5에서는 디폴트로 public
	@Test
	public void helloworld(){
//		System.out.println("test");
		System.out.println(helloController.index());

		assertThat(helloController.index(), is("Hello world!"));
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void mockMvcTest() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(helloController).build();

		mvc.perform(
				MockMvcRequestBuilders.get("/index"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Hello world!"));

	}
}
