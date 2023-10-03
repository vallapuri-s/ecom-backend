package com.thoughtworks.ecombackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String category;

    @NotNull
    private Double price;

    private String description;

    @NotNull
    @Size(max = 255)
    private String manufacturer;

    @NotNull
    private Integer availableItems;

    @OneToMany(fetch = FetchType.LAZY)
    @NotNull
    private List<LineItem> lineItems;

    @Size(max = 255)
    private String imageUrl;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;
}
