package com.spring.challenge.repositories;

import com.spring.challenge.dtos.ProductDto;
import com.spring.challenge.dtos.TicketDto;
import com.spring.challenge.exceptions.ApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductRepository {
    public List<ProductDto> getProducts();
    public HashMap<Integer, ProductDto> getProductsForPurchase(List<Integer> ids);

    TicketDto mergeTicket(TicketDto ticket) throws ApiException;
}
