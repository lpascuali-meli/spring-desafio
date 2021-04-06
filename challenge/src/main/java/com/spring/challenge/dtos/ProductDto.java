package com.spring.challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer quantity;
    private Boolean freeShipping;
    private Integer prestige;
}
