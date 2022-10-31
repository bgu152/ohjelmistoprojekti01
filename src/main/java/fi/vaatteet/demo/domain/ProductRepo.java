package fi.vaatteet.demo.domain;

import org.springframework.data.repository.CrudRepository;

//import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends CrudRepository<Product, Long> {

}
