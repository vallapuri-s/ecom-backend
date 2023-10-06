package com.thoughtworks.ecombackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LineItemDto {
    private Long id;
    private Long productId;
    private int quantity;
}
