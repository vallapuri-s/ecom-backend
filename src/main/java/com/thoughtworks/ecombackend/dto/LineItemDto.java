package com.thoughtworks.ecombackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LineItemDto {
    private String id;
    private String productId;
    private int quantity;
}
