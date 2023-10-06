package com.thoughtworks.ecombackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "line_items")
@AllArgsConstructor
@NoArgsConstructor
public class LineItem {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id", referencedColumnName ="id", nullable=false)
    @NotNull
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="order_id",referencedColumnName = "id", nullable=false)
    private Order order;
}
