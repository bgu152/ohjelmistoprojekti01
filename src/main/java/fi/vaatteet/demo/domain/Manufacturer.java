package fi.vaatteet.demo.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Manufacturer {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    private String name;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manufacturer")
    private List<Product> products;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public Manufacturer() {
		super();
	}
	public Manufacturer(String name) {
		super();
		this.name = name;
	}
	@Override
	public String toString() {
		return "Manufacturer [id=" + id + ", name=" + name + "]";
	}
	
	
    
    
}


