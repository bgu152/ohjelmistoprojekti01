package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fi.vaatteet.demo.DemoApplication;

@RunWith(SpringRunner.class)
//@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = { DemoApplication.class })
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
class RestTests {
	
	String productURL = "http://localhost:8080/api/products/";

	@Autowired
	private WebApplicationContext webAppContext;

	private MockMvc mocksMvc;

	@BeforeEach
	public void setup() {
		mocksMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

	}

	@Test
	public void apiStatusOk() throws Exception {
		mocksMvc.perform(get(productURL)).andExpect(status().isOk());
	}

	@Test
	public void responseTypeApplicationJson() throws Exception {
		mocksMvc.perform(get(productURL))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void checkProductInfo() throws Exception {
		long id = 4;
		mocksMvc.perform(get(productURL + id))
		.andExpect(jsonPath("$.name").value("JoustavaMeno"))
		.andExpect(jsonPath("$.type").value("haalari"))
		.andDo(print());
	}
}
