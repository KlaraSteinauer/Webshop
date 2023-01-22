package com.webshop.webshop.repository;

import com.webshop.webshop.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
