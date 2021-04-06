package com.spring.challenge.services;

import com.spring.challenge.dtos.ProductDto;
import com.spring.challenge.dtos.TicketDto;
import com.spring.challenge.exceptions.ApiException;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductDto> getProductsByFilters(Map<String, String> filters) throws ApiException;
    TicketDto createPurchaseRequest(List<ProductDto> products) throws ApiException;
}
