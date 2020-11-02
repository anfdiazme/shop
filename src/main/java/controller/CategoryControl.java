package controller;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.models.Category;

public interface CategoryControl extends  JpaRepository <Category, Integer> {

}

