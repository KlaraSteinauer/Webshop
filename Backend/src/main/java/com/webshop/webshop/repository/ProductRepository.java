package com.webshop.webshop.repository;

import com.webshop.webshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//Enthält alle "einfachen" Funktionen die mit der Datenbank verbunden sind.
//Ähnlich den SQL Statements.(select/insert/delete/update)


//JpaRepository extends CrudRepository (kind of)

/*
 * All annotated classes (e.g. @Component, @Service, @Repository...) are beans that will be created by Spring framework
 * Benefits: this way, spring creates the class only once, and you can use it everywhere.. without the need to
 * instantiate it over and over again.
 * <p>
 * In order to use those Components, you need to declare them as an attribute in the class you want
 * and to annotate it with @Autowired
 * e.g.
 *
 * @Autowired private ProductRepository productRepository;
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByDescription(String productDescription);

    List<Product> findByCategory(String productCategory);
}
