package fi.vaatteet.demo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import fi.vaatteet.demo.domain.ProductRepo;

@CrossOrigin
@Controller
public class ProductController {
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private ManufacturerRepo manufacturerRepo;

	// Web pages
	@RequestMapping(value = { "/" })
	public String indexPage(Model model) {
		return "index";
	}

	// Web page with list of products
	@RequestMapping(value = { "/products" })
	public String productList(Model model) {
		model.addAttribute("products", productRepo.findAll());
		return "productList";
	}

	// Show all products by chosen manufacturer
	@RequestMapping(value = "/products/{manufacturer}", method = RequestMethod.GET)
	public String getProductsByManufacturer(@PathVariable("manufacturer") String name, Model model) {
		model.addAttribute("manufacturer", name);
		model.addAttribute("products", manufacturerRepo.findByName(name).getProducts());
		return "productList";
	}

//  Delete in web page. Return to the page the request was made from
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/delete/{id}", "/{manufacturer}/delete/{id}" }, method = RequestMethod.GET)
	public String deleteProduct(@PathVariable Long id, @PathVariable(required = false) String manufacturer,
			Model model) {
		productRepo.deleteById(id);
		if (manufacturer != null) {
			return "redirect:/products/{manufacturer}";
		}
		return "redirect:../products";
	}

	// Add new product
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/add")
	public String addProduct(Model model) {
		String title2 = "Lisää vaate";
		List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerRepo.findAll();
		model.addAttribute("title", title2);
		model.addAttribute("product", new Product());
		model.addAttribute("manufacturers", manufacturers);
		return "addProduct";
	}

	// Save new product in webpage
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Product product) {
		try {
			if (product.getPrice() == null || product.getPrice() < 0) {
				System.out.println("Failed, price too small");
				return "redirect:add";
			} else {
				System.out.println(product.toString());
				productRepo.save(product);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println('2');
			return "addProduct";
		}
		return "redirect:products";
	}

	// Update new product
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/edit/{id}")
	public String updateProduct(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "title", required = false) String title) {
		Optional<Product> product = productRepo.findById(id);
		if (product.isEmpty()) {
			return "redirect:../products";
		}
		List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerRepo.findAll();
		model.addAttribute("manufacturers", manufacturers);
		String title1 = "Muokkaa vaatetta";
		model.addAttribute("product", product);
		model.addAttribute("title", title1);
		return "addProduct";
	}

	// error
	@Controller
	public class Error implements ErrorController {
		@RequestMapping("/error")
		public String handleError() {
			return "error";
		}
	}

	// Restful services below

	@RequestMapping("api/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> productlist = (List<Product>) productRepo.findAll();
			return ResponseEntity.ok().body(productlist);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@RequestMapping("api/products/{id}")
	public ResponseEntity<Optional<Product>> getProduct(@PathVariable Long id) {
		try {
			Optional<Product> product = productRepo.findById(id);
			return ResponseEntity.ok().body(product);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@DeleteMapping("api/products/{id}")
	public ResponseEntity<Optional<Product>> deleteProduct(@PathVariable Long id) {
		try {
			Optional<Product> product = productRepo.findById(id);
			if (product.isEmpty()) {
				return ResponseEntity.status(400).build();
			}
			productRepo.deleteById(id);
			return ResponseEntity.ok().body(product);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("api/products/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product productUpdated, @PathVariable Long id) {

		try {
			Optional<Product> productFromDb = productRepo.findById(id);
			if (productFromDb.isEmpty()) {
				System.out.println(productUpdated);
				return ResponseEntity.status(400).build();
			}

			Optional<Manufacturer> manufacturer = manufacturerRepo.findById((productUpdated.getManufacturer().getId()));

			if (manufacturer.isEmpty()) {
				return ResponseEntity.status(400).build();
			}

			productUpdated.setId(id);

			return ResponseEntity.ok().body(productRepo.save(productUpdated));

		} catch (Exception e) {
			return ResponseEntity.status(500).build();

		}
	}

	@PostMapping("api/products/")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {

		try {
			Optional<Manufacturer> manufacturer = manufacturerRepo.findById((product.getManufacturer().getId()));

			if (manufacturer.isEmpty()) {
				return ResponseEntity.status(400).build();
			}
			return ResponseEntity.ok().body(productRepo.save(product));
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
}