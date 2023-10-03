package com.thoughtworks.ecombackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Entity
@Table(name = "line_item")
public class LineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotNull
    private Integer quantity;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "lineItems")
    @NotNull
    private Product product;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "lineItems")
    @NotNull
    private Order order;

}
