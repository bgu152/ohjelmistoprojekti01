package fi.vaatteet.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.vaatteet.demo.domain.ManufacturerRepo;

import fi.vaatteet.demo.domain.Manufacturer;

import java.util.Optional;
import java.util.List;



@Controller
public class ManufacturerController {
	@Autowired
	private ManufacturerRepo manufacturerRepo;
	
	//Rest
	@GetMapping("api/manufacturer")
	public ResponseEntity<List<Manufacturer>> getAllManufacturers(){
		try {
			List<Manufacturer> manufacturers = (java.util.List<Manufacturer>) manufacturerRepo.findAll();
			return ResponseEntity.ok().body(manufacturers);			
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
			
		}		
	}
	
	@GetMapping("api/manufacturer/{id}")
	public ResponseEntity<Optional<Manufacturer>> getManufacturer(@PathVariable Long id){
		try {
			Optional<Manufacturer> manufacturer = manufacturerRepo.findById(id);
			return ResponseEntity.ok().body(manufacturer);				
		}catch(Exception e) {
			return ResponseEntity.status(400).build();
		}		
	}
	
	@DeleteMapping("api/manufacturer/{id}")
	public ResponseEntity<Optional<Manufacturer>> deleteManufacturer(@PathVariable Long id){
		try {
			Optional<Manufacturer> manufacturer =  manufacturerRepo.findById(id);
			if(manufacturer.isEmpty()) {
				return ResponseEntity.ok().build();
			}
			manufacturerRepo.deleteById(id);		
			return ResponseEntity.status(204).build(); 		
		}catch(Exception e) {
			return ResponseEntity.status(500).build();			
		}	
	}
	
	@PutMapping("api/manufacturer/{id}")
	public ResponseEntity<Manufacturer> updateManufacturer(@RequestBody Manufacturer manufacturerUpdated, @PathVariable Long id){
		Optional<Manufacturer> manufacturerFromDb = manufacturerRepo.findById(id);
		if(manufacturerFromDb.isEmpty()) {
			return ResponseEntity.status(400).build();
		}
		
		manufacturerUpdated.setId(id);
		return ResponseEntity.ok().body(manufacturerUpdated);
	}

}
