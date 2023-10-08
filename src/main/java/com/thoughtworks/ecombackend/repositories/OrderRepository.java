package com.thoughtworks.ecombackend.repositories;

import com.thoughtworks.ecombackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
