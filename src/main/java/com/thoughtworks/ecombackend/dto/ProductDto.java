package com.thoughtworks.ecombackend.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class ProductDto {

    private String id;

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

    @Size(max = 255)
    private String imageUrl;

}
