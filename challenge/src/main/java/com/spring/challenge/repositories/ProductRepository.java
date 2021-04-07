package com.spring.challenge.repositories;

import com.spring.challenge.dtos.ProductDto;
import com.spring.challenge.dtos.TicketDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductRepository {
    public List<ProductDto> getProductsByFilters(Map<String, String> filters);
    public HashMap<Integer, ProductDto> getProductsForPurchase(List<Integer> ids);

    TicketDto createTicket(TicketDto ticket);
}
