package com.spring.challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductForTicketDto {
    private Integer productId;
    private String name;
    private String brand;
    private Integer quantity;
}
