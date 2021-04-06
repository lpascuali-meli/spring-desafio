package com.spring.challenge.repositories;

import com.spring.challenge.dtos.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ProductRepository {
    public List<ProductDto> getProductsByFilters(Map<String, String> filters);
    public List<ProductDto> getProductsForPurchase(List<Integer> ids);
}
