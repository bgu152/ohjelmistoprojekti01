package fi.vaatteet.demo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.vaatteet.demo.domain.Manufacturer;
import fi.vaatteet.demo.domain.ManufacturerRepo;
import fi.vaatteet.demo.domain.Product;

@CrossOrigin
@Controller
public class ManufacturerController {
	@Autowired
	private ManufacturerRepo manufacturerRepo;
	
	// Web pages
	// Web page with list of manufacturers
	@RequestMapping(value = { "/manufacturers" }, method = RequestMethod.GET)
	public String manufacturerList(Model model) {
		model.addAttribute("manufacturers", manufacturerRepo.findAll());
		return "manufacturerList";
	}
	
	// Delete manufacturer in web page
	@RequestMapping(value = "/deletemanufacturer/{id}", method = RequestMethod.GET)
	public String deleteManufacturer(@PathVariable("id") Long id, Model model) {
		manufacturerRepo.deleteById(id);
		return "redirect:../manufacturers";
	}
	
	// Update new product
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/editmanufacturer/{id}")
	public String updateManufacturer(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "title", required = false) String title) {
		Optional<Manufacturer> manuf = manufacturerRepo.findById(id);
		if (manuf.isEmpty()) {
			return "redirect:../manufacturers";
		}
		String title1 = "Muokkaa vaatetta";
		model.addAttribute("manufacturer", manuf);
		model.addAttribute("title", title1);
		return "addManufacturer";
	}
	
	// Add new manufacturer
	@RequestMapping(value = "/addmanufacturer")
	public String addManufacturer(Model model) {
		model.addAttribute("manufacturer", new Manufacturer());
		return "addManufacturer";
	}
	
	// Save new manufacturer
	@RequestMapping(value = "/savemanufacturer", method = RequestMethod.POST)
	public String saveManufacturer(Manufacturer manufacturer) {
		try {
			manufacturerRepo.save(manufacturer);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "redirect:/manufacturers";
	}
	
	//Rest
	
	@PostMapping("api/manufacturers")
	public ResponseEntity<Manufacturer> postManufacturer(@RequestBody Manufacturer manufacturer){
		try {
			return ResponseEntity.status(203).body(manufacturerRepo.save(manufacturer));
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
	
	
	@GetMapping("api/manufacturers")
	public ResponseEntity<List<Manufacturer>> getAllManufacturers(){
		try {
			List<Manufacturer> manufacturers = (java.util.List<Manufacturer>) manufacturerRepo.findAll();
			return ResponseEntity.ok().body(manufacturers);			
		}catch(Exception e) {
			return ResponseEntity.status(500).build();			
		}		
	}
	
	@GetMapping("api/manufacturers/{id}")
	public ResponseEntity<Optional<Manufacturer>> getManufacturer(@PathVariable Long id){
		try {
			Optional<Manufacturer> manufacturer = manufacturerRepo.findById(id);
			return ResponseEntity.ok().body(manufacturer);				
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}		
	}
	
	@DeleteMapping("api/manufacturers/{id}")
	public ResponseEntity<Optional<Manufacturer>> deleteManufacturer(@PathVariable Long id){
		try {
			Optional<Manufacturer> manufacturer =  manufacturerRepo.findById(id);
			if(manufacturer.isEmpty()) {
				return ResponseEntity.ok().build();
			}
			manufacturerRepo.deleteById(id);		
			return ResponseEntity.status(204).body(manufacturer); 		
		}catch(Exception e) {
			return ResponseEntity.status(500).build();			
		}	
	}
	
	@PutMapping("api/manufacturers/{id}")
	public ResponseEntity<Manufacturer> updateManufacturer(@RequestBody Manufacturer manufacturerUpdated, @PathVariable Long id){
		Optional<Manufacturer> manufacturerFromDb = manufacturerRepo.findById(id);
		try {
			if(manufacturerFromDb.isEmpty()) {
				return ResponseEntity.status(400).build();
			}			
			manufacturerUpdated.setId(id);
			return ResponseEntity.ok().body(manufacturerUpdated);	
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

}
