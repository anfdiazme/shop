package controller;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.models.Product;

public interface ProductControl extends JpaRepository <Product, Integer> {

}
