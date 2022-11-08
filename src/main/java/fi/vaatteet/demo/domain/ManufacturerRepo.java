package fi.vaatteet.demo.domain;

import org.springframework.data.repository.CrudRepository;

public interface ManufacturerRepo extends CrudRepository<Manufacturer, Long> {
	Manufacturer findByName(String name);
}
