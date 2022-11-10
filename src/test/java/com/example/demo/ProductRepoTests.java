package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import fi.vaatteet.demo.DemoApplication;
import fi.vaatteet.demo.domain.Manufacturer;
import fi.vaatteet.demo.domain.Product;
import fi.vaatteet.demo.domain.ProductRepo;

@ContextConfiguration(classes = DemoApplication.class)
@DataJpaTest
class ProductRepoTests {
	
	@Autowired
	ProductRepo productRepo;
	
	@Test
	public void FindClothesname() {
		
		Product product = productRepo.findById((long) 4).get();
		assertEquals(product.getName(), "JoustavaMeno");
	}
	
	@Test
	public void FindClothesManufacturer() {
		
		Manufacturer manuF = productRepo.findById((long) 4).get().getManufacturer();
		//String produsct = productRepo.findById((long) 3).get().getName();
		assertEquals(manuF.getName(), "M&M");
	}


}
