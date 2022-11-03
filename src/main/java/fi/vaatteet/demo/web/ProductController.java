package fi.vaatteet.demo.web;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.vaatteet.demo.domain.Product;
import fi.vaatteet.demo.domain.ProductRepo;

import org.springframework.http.ResponseEntity;


@Controller
public class ProductController {
	@Autowired
	private ProductRepo productRepo;

	//Web pages
    @RequestMapping(value = {"/"})
    public String indexPage(Model model) {
        return "index";
    }
    
	//Web page with list of products
    @RequestMapping(value = {"/products"})
    public String productList(Model model) {
    	model.addAttribute("products", productRepo.findAll());
        return "productList";
    }
    
//  Delete in webpage
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
    	productRepo.deleteById(id);
        return "redirect:../products";	
    }     
    
    // Add new product
    @RequestMapping(value = "/add")
    public String addProduct(Model model){
   		String title2 = "Lisää vaate";
   		model.addAttribute("title", title2);
    	model.addAttribute("product", new Product());
        return "addProduct";
    }    
        
    // Save new product in webpage
       @RequestMapping(value = "/save", method = RequestMethod.POST)
       public String save(Product product){
       	try {
       		System.out.println(product.toString());
               productRepo.save(product);}
       	catch(Exception e) {
       		  System.out.println(e.toString());
       		}
           return "redirect:products";
       } 
       
    
    // Update new product
    @RequestMapping(value = "/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, Model model, @RequestParam(value="title", required=false) String title){
    	Optional<Product> product = productRepo.findById(id);
    	if(product.isEmpty()) {
            return "redirect:../products";	
    	}    	
    	String title1 = "Muokkaa vaatetta";
    	model.addAttribute("product", product);
    	model.addAttribute("title", title1);
        return "addProduct";
    }
    
    
    //error
    @Controller
    public class Error implements ErrorController {
    	@RequestMapping("/error")
    	public String handleError() {
    		return "error";
    	}
    	
    }
    
    //Restful services below
    
    @RequestMapping("/product")
    public @ResponseBody List<Product> getAllProducts() { 
    	List<Product> productlist = (List<Product>) productRepo.findAll();    	
		return productlist;   	
    }
    
    @RequestMapping("/product/{id}")
    public @ResponseBody Optional<Product> getProduct(@PathVariable Long id) {
    	Optional<Product> product = productRepo.findById(id);
    	return product;
    }
    
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Optional<Product>> deleteProduct(@PathVariable Long id){
    	Optional<Product> product = productRepo.findById(id);
    	if(product.isEmpty()) {
    		return ResponseEntity.status(400).build();
    	}
    	productRepo.deleteById(id);
    	return ResponseEntity.ok().body(product);
    }
    
    @PutMapping("/product/{id}")
    public @ResponseBody ResponseEntity<Product> updateProduct(@RequestBody Product productUpdated, @PathVariable Long id) {
    	Optional<Product> productFromDb = productRepo.findById(id); 
    	if(productFromDb.isEmpty()) {
    		return ResponseEntity.status(400).body(null);
    	}
    	productUpdated.setId(id);
    	System.out.println(productUpdated);
    	return ResponseEntity.ok().body(productRepo.save(productUpdated));
    }
    
    @PostMapping("/product/")
    public @ResponseBody  ResponseEntity<Product> addProduct(@RequestBody Product product){
    	return ResponseEntity.ok().body(productRepo.save(product));    	
    }    
}