package com.webshop.webshop.repository;

        import com.webshop.webshop.model.Product;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.util.List;

//Enthält alle "einfachen" Funktionen die mit der Datenbank verbunden sind.
//Ähnlich den SQL Statements.(select/insert/delete/update)


//JpaRepository extends CrudRepository (kind of)
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductDescription(String productDescription);
    List<Product> findByProductCategory(String productCategory);
    List<Product> findByProductName(String ProductName);


}
