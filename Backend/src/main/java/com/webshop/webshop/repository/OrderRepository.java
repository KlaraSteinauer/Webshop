package com.webshop.webshop.repository;

import com.webshop.webshop.model.KimOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<KimOrder, Long> {
}
