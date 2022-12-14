package fi.vaatteet.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import fi.vaatteet.demo.domain.Manufacturer;
import fi.vaatteet.demo.domain.ManufacturerRepo;
import fi.vaatteet.demo.domain.Product;
import fi.vaatteet.demo.domain.ProductRepo;
import fi.vaatteet.demo.domain.User;
import fi.vaatteet.demo.domain.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;


@SpringBootApplication 
public class DemoApplication {
private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
		public CommandLineRunner productDemo(ProductRepo productRepo, ManufacturerRepo manufacturerRepo, UserRepo userRepo) {
			return (args) -> {
				
				log.info("Saving a user");
				userRepo.save(new User("omppu", "$2a$12$NQpB3C8959BKZgJvqYxBf.MVzBELr34eTrw1l1bTFiOz.M6bmI2vW", "ADMIN"));
				userRepo.save(new User("rane", "$2a$12$IfwOH0AT/FFWYeXl3G.e1.AEcU63P5VLP.MHYQjnM.DF1Px6KUWwe", "USER"));

				log.info("save a couple of manufacturers");
				manufacturerRepo.save(new Manufacturer(" "));
				Manufacturer mnm = manufacturerRepo.save(new Manufacturer("M&M"));
				Manufacturer leikki = manufacturerRepo.save(new Manufacturer("Leikki"));
								
				log.info("save a couple of products");
				productRepo.save(new Product("JoustavaMeno", "haalari",  (float) 59, mnm));
				productRepo.save(new Product("70-luku", "haalari", (float) 32, leikki));
				productRepo.save(new Product("LämpöisäHaukku", "talvihaalari", (float) 92, leikki));	

				for (Product product: productRepo.findAll()) {
					log.info(product.toString());
				};			
			};
	}
}