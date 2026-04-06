package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Caută dacă numele SAU SKU-ul conțin textul introdus (ignoring case)
    List<Product> findByNumeContainingIgnoreCaseOrSkuContainingIgnoreCase(String nume, String sku);
}