package com.thoughtworks.ecombackend.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotNull
    private Integer quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    @NotNull
    private List<LineItem> lineItems;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

}
