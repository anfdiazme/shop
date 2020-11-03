package com.shop.rest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.shop.model.*;
import com.shop.control.*;
@RestController
public class ShopRest {
	@Autowired
	private ProductControl productc;
	@Autowired
	private CategoryControl categoryc;
	@PostMapping("/newcategory")
	public void newcategory(@RequestBody Category category) {
		String father=category.getFather();
		List <Category> categories = categoryc.findAll();
		if (father==null) {
			categoryc.save(category);
		}else {
			for(Category x:categories) {
				if(father.equals(x.getName())) {
					int id=x.getId();
					category.setId_father(id);
					categoryc.save(category);
				}
			}
		}
	}
	@GetMapping("/allcategory")
	public List<Category> getCategory() {
		List <Category> category = categoryc.findAll();
		List <Category> categories = new ArrayList <Category>();
		for(Category x: category) {
			if(x.getId_father()==0){
				categories.add(x);
			}	
		}
		return categories;
	}
	@GetMapping("/allcategories")
	public List<Category> getCategories() {
		return categoryc.findAll();
	}
	@GetMapping("/subcategory/{id}")
	public List<Category> getSubCategory(@PathVariable int id) {
		List <Category> category = categoryc.findAll();
		List <Category> categories = new ArrayList <Category>();
		for(Category x: category) {
			if(x.getId_father()==id){
				categories.add(x);
			}	
		}
		return categories;
	}
	@GetMapping("/categoryson")
	public List <Category> getson(){
		int aux =0;
		List <Category> category = categoryc.findAll();
		List <Category> son = new ArrayList <Category>();
		int len=category.size();
		for (Category x: category) {
			for(Category y: category) {
				if(x.getId()==y.getId_father()) {
					aux=0;
					break;
				}else {
					aux++;
				}
			}
			if(aux==len) {
				son.add(x);
				aux=0;
			}
		}
		return son;
	}
	@PostMapping("/newproduct")
	public String newproduct(@RequestBody Product product) {
		Integer categoryid=product.getId_category();
		List <Category> categories = categoryc.findAll();
		for (  Category x : categories ) {
			if(x.getId_father()==categoryid) {
				return"In this part is not posible include a product";
			}
		}
		productc.save(product);
		return "new product are include";
	}
	@GetMapping("/productsbyc/{id}")
	public List<Product> getproductsbycategory(@PathVariable int id){
		List <Product> product =productc.findAll();
		List <Product> productbycategorie=new ArrayList < Product> ( ) ; 
		for(Product x:product) {
			if(x.getId_category()==id) {
				productbycategorie.add(x);
			}
		}
		return productbycategorie;
	}
	@GetMapping("/product/{id}")
	public Optional<Product> getproduct(@PathVariable int id) {
		Optional<Product> product=productc.findById(id);
		return product;
	}
	
}