package com.thoughtworks.ecombackend.repositories;

import com.thoughtworks.ecombackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    void delete(Product deleted);
}
