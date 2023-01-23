package com.webshop.webshop.repository;

import com.webshop.webshop.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Enthält alle "einfachen" Funktionen die mit der Datenbank verbunden sind.
//Ähnlich den SQL Statements.(select/insert/delte/update)

public interface ProductRepository extends CrudRepository<Product, Long> {


}
