package com.thoughtworks.ecombackend.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
public class OrderDto {

    private String id;

    @NotNull
    private String userId;

    @NotNull
    private List<LineItemDto> lineItems;

}
