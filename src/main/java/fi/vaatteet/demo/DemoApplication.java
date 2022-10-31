package fi.vaatteet.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.vaatteet.demo.domain.Product;
import fi.vaatteet.demo.domain.ProductRepo;

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
		public CommandLineRunner productDemo(ProductRepo repo) {
			return (args) -> {
				log.info("save a couple of products");
				repo.save(new Product("JoustavaMeno", "haalari",  (float) 59, "M&M"));
				repo.save(new Product("70-luku", "haalari", (float) 32, "Leikki"));
				repo.save(new Product("LämpöisäHaukku", "talvihaalari", (float) 92, "Leikki"));

				for (Product product: repo.findAll()) {
					log.info(product.toString());
				};			
			};
	}
	

}
