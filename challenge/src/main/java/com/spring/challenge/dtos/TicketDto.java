package com.spring.challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private long id;
    private List<ProductForTicketDto> articles;
    private double total;
}
