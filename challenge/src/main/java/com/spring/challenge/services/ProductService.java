package com.spring.challenge.services;

import com.spring.challenge.dtos.OrderForTicketDto;
import com.spring.challenge.dtos.ProductDto;
import com.spring.challenge.dtos.TicketResponse;
import com.spring.challenge.exceptions.ApiException;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductDto> getProductsByFilters(Map<String, String> filters) throws ApiException;
    TicketResponse createPurchaseRequest(OrderForTicketDto orderForTicket) throws ApiException;
}
