package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import fi.vaatteet.demo.DemoApplication;
import fi.vaatteet.demo.web.ManufacturerController;
import fi.vaatteet.demo.web.ProductController;

@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
class DemoApplicationTests {
	
	@Autowired
	ManufacturerController manController;
	@Autowired
	ProductController prodController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(manController).isNotNull();
		assertThat(prodController).isNotNull();
		
	}

}
