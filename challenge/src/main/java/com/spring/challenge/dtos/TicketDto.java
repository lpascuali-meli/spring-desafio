package com.spring.challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private static int id;
    private List<ProductDto> articles;
    private double total;
}
