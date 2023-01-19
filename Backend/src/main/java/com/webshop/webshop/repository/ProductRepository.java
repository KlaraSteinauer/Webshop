package com.webshop.webshop.repository;

import com.webshop.webshop.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
